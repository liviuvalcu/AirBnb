package com.reply.airbnbdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static  org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static  org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static  org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(value = PropertyController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class, useDefaultFilters = false)
@Import(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createProperty() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/property/create")
                        .content(objectMapper.writeValueAsString(PropertyBean.builder().propertyName("Test").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andDo(print())
                        .andReturn();
    }

    @Test
    void getAllPropertiesByUserEmailOrCountry() {
    }

    @Test
    void testGetAllPropertiesByUserEmailOrCountry() {
    }
}