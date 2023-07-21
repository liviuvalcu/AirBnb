package com.reply.airbnbdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.dao.JwtAuthenticationResponse;
import com.reply.airbnbdemo.dao.RegistrationDao;
import com.reply.airbnbdemo.dao.SignUpRequest;
import com.reply.airbnbdemo.service.RegistrationService;
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

import static org.junit.jupiter.api.Assertions.*;
import static  org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static  org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;
@WebMvcTest(value = RegistrationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class, useDefaultFilters = false)
@Import(RegistrationController.class)
class RegistrationControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void registerUser() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder().firstName("F_NAME").lastName("L_NAME").password("***").email("F_NAME.L_NAME@gmail.com").build();
        RegistrationDao registrationDao = RegistrationDao.builder().signUpRequest(signUpRequest).build();

        JwtAuthenticationResponse response = new JwtAuthenticationResponse("AAABBBCCC0001");

        when(registrationService.registerUser(any(RegistrationDao.class))).thenReturn(response);

       MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/registration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDao))
                ).andExpect(status().isOk())
                .andDo(print())
                .andReturn();

       String actualResponseBody = mvcResult.getResponse().getContentAsString();

       assertEquals(objectMapper.writeValueAsString(response), actualResponseBody);

    }
}