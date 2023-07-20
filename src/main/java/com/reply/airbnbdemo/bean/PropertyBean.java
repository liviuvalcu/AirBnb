package com.reply.airbnbdemo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reply.airbnbdemo.model.Host;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyBean {


    private String propertyName;
    private Integer zipcode;
    private Integer bathroomCnt;
    private Integer bedroomCnt;
    private Integer guestNum;
    private BigDecimal pricePerNight;
    private BigDecimal cleaningFee;
    private LocalDate created;
    private Instant checkInTime;
    private Instant checkOutTime;
    private Character isRefundable;
    private Integer cancellationPeriod;
    private String cancellationType;
    private BigDecimal refundRate;
    private String street;
    private String city;
    private String stateofResidence;
    private String country;
    private BigDecimal taxRate;

    private String hostEmail;

}
