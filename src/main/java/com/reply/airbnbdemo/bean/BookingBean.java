package com.reply.airbnbdemo.bean;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingBean {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer seniorGuestNum;
    private Integer adultGuestNum;
    private Integer childGuestNum;
    private String guestEmail;
    private String propertyName;
}
