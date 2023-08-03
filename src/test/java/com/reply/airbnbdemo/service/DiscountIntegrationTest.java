package com.reply.airbnbdemo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.airbnbdemo.AirBnbDemoApplication;
import com.reply.airbnbdemo.bean.BookingBean;
import com.reply.airbnbdemo.dto.BookingDto;
import com.reply.airbnbdemo.dto.DiscountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.In;
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
        createBooking_without_discount(0);
        //2. Create More Bookings to achieve discount level
        createMultipleBooking_without_discount();
        //3. Create new booking with discount level
        createBooking_with_discount(25);
        //4. Update discount level
        updateDiscountLevel("SILVER");
        //5. Create booking without discount
        createBooking_without_discount(30);
        //6. Check that we have only one booking wit discount
        checkOnlyOneBookingWithDiscount();

    }

    private void createBooking_without_discount(Integer numberOfDays) throws Exception {

        BookingBean bookingBean = getBookingBean(Objects.isNull(numberOfDays) ? 0 : numberOfDays);

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
                .checkInDate(LocalDate.now().plusDays(1 + addDaysAmount))
                .checkOutDate(LocalDate.now().plusDays(2 + addDaysAmount))
                .seniorGuestNum(1)
                .adultGuestNum(1)
                .childGuestNum(1)
                .guestEmail("discount_G@test.com")
                .propertyName("Discount Property")
                .build();
        return bookingBean;
    }

    private void createMultipleBooking_without_discount() throws Exception {
        BigDecimal amountToBeSpent = getSilverTestDiscountAmount();
        int numberOfBookingsToBeCreated = amountToBeSpent.divideToIntegralValue(new BigDecimal(100)).round(new MathContext(5, RoundingMode.UP)).intValue() - 1;

        int days = 2;
        for(int i=0; i < numberOfBookingsToBeCreated; i++){
            createBooking_without_discount(days);
            days += 2;
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

    private void createBooking_with_discount(Integer numberOfDays) throws Exception {
        BookingBean bookingBean = getBookingBean(Objects.isNull(numberOfDays) ? 0 : numberOfDays);

         mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/booking/create")
                        .content(objectMapper.writeValueAsString(bookingBean))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

    }

    private void updateDiscountLevel(String discountLevel) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parameters/updateAmountByName")
                        .param("name","Silver discount test")
                        .param("minimalAmountSpent", "800")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    private void checkOnlyOneBookingWithDiscount() throws Exception {

        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/booking/byPropertyName")
                        .param("propertyName","Discount Property")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertNotNull(actualResponseBody);
        Set<Integer> amounts = new HashSet<>();
          objectMapper.readValue(actualResponseBody, new TypeReference<List<BookingDto>>() {})
                .stream().forEach(booking -> {
                      amounts.add(booking.getAmountPaid().intValue());
                  });
          assertEquals(2, amounts.size());
    }
}
