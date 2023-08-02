package com.reply.airbnbdemo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.airbnbdemo.AirBnbDemoApplication;
import com.reply.airbnbdemo.bean.BookingBean;
import com.reply.airbnbdemo.dto.DiscountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AirBnbDemoApplication.class)
@AutoConfigureMockMvc
@Sql("classpath:discountInit.sql")
public class DiscountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser
    public void createBookingBasedOnDiscount() throws Exception {

        //1. Create Booking without discounts
        createBooking_without_discount();
        //2. Create More Bookings to achieve discount level
        createBooking_without_discount(200);
        //3. Create new booking with discount level
        createBooking_with_discount();
        //4. Update discount level
        updateDiscountLevel("");
        //5. Create booking without discount
        createBooking_without_discount();

    }

    private void createBooking_without_discount() throws Exception {
        BookingBean bookingBean = getBookingBean(10);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/booking/create")
                        .content(objectMapper.writeValueAsString(bookingBean))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

    }

    private static BookingBean getBookingBean(int addDaysAmount) {
        BookingBean bookingBean = BookingBean
                .builder()
                .checkInDate(LocalDate.now().plusDays(10 + addDaysAmount))
                .checkOutDate(LocalDate.now().plusDays(11))
                .seniorGuestNum(1)
                .adultGuestNum(1)
                .childGuestNum(1)
                .guestEmail("discount_G@test.com")
                .propertyName("Discount Property")
                .build();
        return bookingBean;
    }

    private void createBooking_without_discount(Integer firstLevelDiscountAmount) throws Exception {
        BigDecimal amountToBeSpent = getSilverTestDiscountAmount();
        int numberOfBookingsToBeCreated = amountToBeSpent.divideToIntegralValue(new BigDecimal(100)).round(new MathContext(5, RoundingMode.UP)).intValue();

        for(int i=0; i < numberOfBookingsToBeCreated; i++){
            createBooking_without_discount();
        }


    }

    private BigDecimal getSilverTestDiscountAmount() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parameters/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertNotNull(actualResponseBody);
       return  objectMapper.readValue(actualResponseBody, new TypeReference<List<DiscountDto>>() {})
                .stream()
                .filter(discount -> "Silver discount test".equals(discount.getName()))
                .collect(Collectors.toList()).get(0).getMinimalAmountSpent();
    }

    private void createBooking_with_discount(){

    }

    private void updateDiscountLevel(String discountLevel){

    }
}
