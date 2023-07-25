package com.reply.airbnbdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.dto.DiscountDto;
import com.reply.airbnbdemo.enums.DiscountLevels;
import com.reply.airbnbdemo.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static  org.mockito.Mockito.*;

@WebMvcTest(value = DiscountController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class, useDefaultFilters = false)
@Import(DiscountController.class)
class DiscountControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    private  DiscountService discountService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createDiscount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parameters/create")
                        .param("name","Test name")
                        .param("minimNights","2")
                        .param("minimalAmountSpent","200")
                        .param("discountLevels","GOLD")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
    }

    @Test
    void updateDiscount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/parameters/update")
                        .param("name","Test name")
                        .param("minimNights","2")
                        .param("minimalAmountSpent","200")
                        .param("discountLevels","GOLD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteDiscount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/parameters/delete")
                        .param("discountLevels","SILVER")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void getAll() throws Exception {
        List discounts = List.of(DiscountDto.builder().build(), DiscountDto.builder().build());
        when(discountService.getAll()).thenReturn(discounts);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parameters/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertNotNull(actualResponseBody);
        assertEquals(objectMapper.writeValueAsString(discounts), actualResponseBody);
    }
}