package com.reply.airbnbdemo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto {

    private String propertyName;
    private Integer zipcode;
    private Integer bathroomCnt;
    private Integer bedroomCnt;
    private Integer guestNum;
    private BigDecimal pricePerNight;
    private BigDecimal cleaningFee;
    private Instant checkInTime;
    private Instant checkOutTime;
    private Character isRefundable;
    private Integer cancellationPeriod;
    private String cancellationType;
    private BigDecimal refundRate;


}
