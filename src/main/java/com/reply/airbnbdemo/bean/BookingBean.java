package com.reply.airbnbdemo.bean;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
public class BookingBean {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer seniorGuestNum;
    private Integer adultGuestNum;
    private Integer childGuestNum;
    private String guestEmail;
    private String propertyName;
}
