package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.bean.BookingBean;
import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.dto.BookingDto;
import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.enums.UserType;
import com.reply.airbnbdemo.exception.BookingAlreadyExist;
import com.reply.airbnbdemo.exception.UserMissingException;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.model.Booking;
import com.reply.airbnbdemo.model.Propertylisting;
import com.reply.airbnbdemo.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DiscountService discountService;

    @Value("${booking.user.missing.throwError}")
    private Boolean userMissingThrowError;

    @Value("${airbnb.placeholder}")
    private String placeholder;

    public Booking createBooking(BookingBean bean){
        if(bookingRepository.checkIfCanBeBooked(bean.getCheckInDate(), bean.getPropertyName()) > 0){
            throw new BookingAlreadyExist("Booking not available");
        }

        Booking myBooking = modelMapper.map(bean, Booking.class);
        myBooking.setBookingDate(LocalDate.now());
        myBooking.setId(Objects.isNull(bookingRepository.getMaxId()) ? 1 : bookingRepository.getMaxId() + 1);

        Propertylisting property = propertyService.getPropertyByName(bean.getPropertyName());
        Airbnbuser user = userService.findByEmailEntity(bean.getGuestEmail());

        if(Objects.isNull(user)){
            if(userMissingThrowError){
                throw new UserMissingException("No user found!");
            }else{
               user = userService.insertUser(createUser(bean.getGuestEmail()));
               userService.setUserType(UserType.GUEST, user.getId());
            }
        } else if (Objects.isNull(user.getGuest())) {
            userService.setUserType(UserType.GUEST, user.getId());
        }

        myBooking.setPid(property);
        myBooking.setGuestUID(user.getGuest());
        myBooking.setAmountPaid(new BigDecimal(updatePrice(bean, property)));

        bookingRepository.save(myBooking);
        return myBooking;
    }

    public List<BookingDto> getAllByPropertyName(String propertyName){

        List<Booking> bookings = bookingRepository.findAllByPropertyName(propertyName);

        List<BookingDto> bookingsDto = bookings.stream().map(booking -> {
            String userEmail = booking.getGuestUID().getAirbnbuser().getEmail();
            BookingDto dto = modelMapper.map(booking, BookingDto.class);
            dto.setGuestEmail(userEmail);
            return dto;
        }
        ).collect(Collectors.toList());


        bookingsDto.stream().forEach(bookingDto -> bookingDto.setPropertyName(propertyName));
        return bookingsDto;
    }

    private double updatePrice(BookingBean bean, Propertylisting property){
        Period duration = Period.between(bean.getCheckInDate(), bean.getCheckOutDate());
        BigDecimal pricePerNight = property.getPricePerNight();

        Integer countAmountSpentByUser = bookingRepository.countAmountSpentByUser(bean.getGuestEmail());
        Integer countNightsStayed = bookingRepository.countNightsStayed(bean.getGuestEmail());

        Integer discountForBooking = discountService.getDiscount(countAmountSpentByUser, countNightsStayed);
       //10 - 100 - 10
        return applyDiscountedPrice(duration.getDays() * pricePerNight.longValue(), discountForBooking);
    }

    private Double applyDiscountedPrice(Long price, Integer discount){
        return (double) price - (price * discount)/100;
    }

    private UserBean createUser(String email){
        return UserBean
                .builder()
                .email(email)
                .emEmail(email)
                .userPassword("XXXXX")
                .phone(placeholder)
                .fname(placeholder)
                .emCountryCode(placeholder)
                .build();
    }

}
