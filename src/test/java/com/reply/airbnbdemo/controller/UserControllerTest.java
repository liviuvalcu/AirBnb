package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static  org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static  org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static  org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
//@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsersByEmail() throws Exception {

        when(userService.findByEmail(anyString())).thenReturn(UserDto.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/byEmail")
                        .param("email", "test@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    void getUserByNameAndSurname() {
    }

    @Test
    void insertUser() {
    }

    @Test
    void deleteUserById() {
    }
}