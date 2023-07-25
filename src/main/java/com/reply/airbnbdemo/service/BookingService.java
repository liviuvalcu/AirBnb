package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.bean.BookingBean;
import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.exception.BookingAlreadyExist;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.model.Booking;
import com.reply.airbnbdemo.model.Propertylisting;
import com.reply.airbnbdemo.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

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
            //throw exception or create user/guest
        } else if (Objects.isNull(user.getGuest())) {
            //create guest
        }

        myBooking.setPid(property);
        myBooking.setGuestUID(user.getGuest());
        myBooking.setAmountPaid(new BigDecimal(updatePrice(bean, property)));

        bookingRepository.save(myBooking);
        return myBooking;
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

}
