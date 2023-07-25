package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.dto.DiscountDto;
import com.reply.airbnbdemo.enums.DiscountLevels;
import com.reply.airbnbdemo.model.DiscountModel;
import com.reply.airbnbdemo.repository.DiscountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;

    @Mock
    private  DiscountRepository discountRepository;
    @Mock
    private  ModelMapper modelMapper;

    @Test
    void createDiscount() {

        discountService.createDiscount("Test create", 1, new BigDecimal(100), DiscountLevels.GOLD);
    }

    @Test
    void updateDiscount() {
        when(discountRepository.findByDiscountLevels(any())).thenReturn(DiscountModel.builder().build());
        discountService.updateDiscount("Test update", 1, new BigDecimal(100), DiscountLevels.GOLD);
    }

    @Test
    void deleteDiscount() {
        discountService.deleteDiscount(DiscountLevels.SILVER);
    }

    @Test
    void getAll() {
        when(discountRepository.findAll()).thenReturn(List.of(DiscountModel.builder().build(), DiscountModel.builder().build()));
        when(modelMapper.map(any(), any())).thenReturn(DiscountDto.builder().build());
        List<DiscountDto> discounts =  discountService.getAll();

        assertNotNull(discounts);
        assertEquals(2, discounts.size());
    }

    @Test
    void getDiscount(){
        when(discountRepository.getDiscountByAmountOrNights(10, 100)).thenReturn(121);
       Integer discount = discountService.getDiscount(10, 100);
       assertNotNull(discount);
       assertEquals(121, discount);
    }
}