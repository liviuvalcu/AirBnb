package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.bean.BookingBean;
import com.reply.airbnbdemo.exception.BookingAlreadyExist;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.model.Booking;
import com.reply.airbnbdemo.model.Guest;
import com.reply.airbnbdemo.model.Propertylisting;
import com.reply.airbnbdemo.repository.BookingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    private static final String PROPERTY_NAME = "Test PropertyName";
    private static final String GUEST_EMAIL = "guest@email.com";
    private static final Integer GUEST_ID = 121;


    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PropertyService propertyService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private DiscountService discountService;

    @Test
    @DisplayName("Booking with 10% discount")
    void createBooking() {

        when(bookingRepository.checkIfCanBeBooked(any(), any())).thenReturn(0L);
        when(modelMapper.map(any(), any())).thenReturn(createBookingModel());
        when(bookingRepository.getMaxId()).thenReturn(2);
        when(propertyService.getPropertyByName(anyString())).thenReturn(createPropertyListing());
        when(userService.findByEmailEntity(anyString())).thenReturn(createAirBnbUser());
        when(bookingRepository.countAmountSpentByUser(anyString())).thenReturn(200);
        when(bookingRepository.countNightsStayed(anyString())).thenReturn(10);
        when(discountService.getDiscount(200, 10)).thenReturn(10);


        Booking booking = bookingService.createBooking(createBookingBean());

        assertNotNull(booking);
        assertEquals(900, booking.getAmountPaid().longValue());
        assertEquals(PROPERTY_NAME, booking.getPid().getPropertyName());
        assertEquals(GUEST_ID, booking.getGuestUID().getId());

    }

    @Test
    @DisplayName("Booking with 20% discount")
    void createBooking_2() {

        when(bookingRepository.checkIfCanBeBooked(any(), any())).thenReturn(0L);
        when(modelMapper.map(any(), any())).thenReturn(createBookingModel());
        when(bookingRepository.getMaxId()).thenReturn(2);
        when(propertyService.getPropertyByName(anyString())).thenReturn(createPropertyListing());
        when(userService.findByEmailEntity(anyString())).thenReturn(createAirBnbUser());
        when(bookingRepository.countAmountSpentByUser(anyString())).thenReturn(200);
        when(bookingRepository.countNightsStayed(anyString())).thenReturn(10);
        when(discountService.getDiscount(200, 10)).thenReturn(20);


        Booking booking = bookingService.createBooking(createBookingBean());

        assertNotNull(booking);
        assertEquals(800, booking.getAmountPaid().longValue());
        assertEquals(PROPERTY_NAME, booking.getPid().getPropertyName());
        assertEquals(GUEST_ID, booking.getGuestUID().getId());

    }

    @Test
    @DisplayName("Booking with 30% discount")
    void createBooking_3() {

        when(bookingRepository.checkIfCanBeBooked(any(), any())).thenReturn(0L);
        when(modelMapper.map(any(), any())).thenReturn(createBookingModel());
        when(bookingRepository.getMaxId()).thenReturn(2);
        when(propertyService.getPropertyByName(anyString())).thenReturn(createPropertyListing());
        when(userService.findByEmailEntity(anyString())).thenReturn(createAirBnbUser());
        when(bookingRepository.countAmountSpentByUser(anyString())).thenReturn(200);
        when(bookingRepository.countNightsStayed(anyString())).thenReturn(10);
        when(discountService.getDiscount(200, 10)).thenReturn(30);


        Booking booking = bookingService.createBooking(createBookingBean());

        assertNotNull(booking);
        assertEquals(700, booking.getAmountPaid().longValue());
        assertEquals(PROPERTY_NAME, booking.getPid().getPropertyName());
        assertEquals(GUEST_ID, booking.getGuestUID().getId());

    }

    @Test
    @DisplayName("Exception test")
    void testException(){
        when(bookingRepository.checkIfCanBeBooked(any(), any())).thenReturn(1L);
        Exception exception = assertThrows(BookingAlreadyExist.class, ()->{
            bookingService.createBooking(createBookingBean());
        });

        assertNotNull(exception);
        assertTrue(exception instanceof BookingAlreadyExist);
        assertEquals("Booking not available", exception.getMessage());


    }

    private Booking createBookingModel(){
        return Booking.builder().build();
    }

    private Propertylisting createPropertyListing(){
        return Propertylisting.builder().pricePerNight(new BigDecimal(100)).propertyName(PROPERTY_NAME).build();
    }

    private Airbnbuser createAirBnbUser(){
        return Airbnbuser.builder().guest(createGuest()).build();
    }

    private Guest createGuest(){
        return Guest.builder().id(GUEST_ID).build();
    }

    private BookingBean createBookingBean(){
        return BookingBean.builder()
                .checkInDate(LocalDate.now().plusDays(20))
                .checkOutDate(LocalDate.now().plusDays(30))
                .propertyName(PROPERTY_NAME)
                .guestEmail(GUEST_EMAIL)
                .build();
    }
}