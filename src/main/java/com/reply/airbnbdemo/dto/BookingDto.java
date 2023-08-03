package com.reply.airbnbdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.reply.airbnbdemo.model.Booking} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer seniorGuestNum;
    private Integer adultGuestNum;
    private Integer childGuestNum;
    private String guestEmail;
    private String propertyName;
    private BigDecimal amountPaid;
}