package com.reply.airbnbdemo.dto;

import com.reply.airbnbdemo.enums.DiscountLevels;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {

    private String name;
    private Integer minimNights;
    private BigDecimal minimalAmountSpent;
    private DiscountLevels discountLevels;
    private Integer discount;
}
