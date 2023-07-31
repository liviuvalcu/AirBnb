package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.AirBnbDemoApplication;
import com.reply.airbnbdemo.TestConfig;
import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.controller.WishListController;
import com.reply.airbnbdemo.enums.UserType;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.utils.ObjectCreatorUtility;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AirBnbDemoApplication.class)
@Testcontainers
@AutoConfigureMockMvc
//@Sql(scripts = {"classpath:init.sql"})
public class WishListIntegrationTest {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0-debian"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    PropertyService propertyService;

    private static final String EMAIL = "test@gmail.com";
    private static final String EMAIL_HOST = "test_H@gmail.com";

    @BeforeEach
    public void setUp(){
     createBnBUser(UserType.GUEST, EMAIL);
     createBnBUser(UserType.HOST, EMAIL_HOST);
     ObjectCreatorUtility.getListOfPropertyNames().forEach(propertyName -> createProperty(propertyName, EMAIL_HOST));

    }



    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
        registry.add("spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> mySQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> mySQLContainer.getPassword());
        registry.add("spring.flyway.enabled", () -> "true");
    }

    @Test
    @DisplayName("Test wishlist flow")
    @WithMockUser(value = "test@gmail.com")
    public void testWishListFlow() throws Exception {
        //1. Create wishlist
        MultiValueMap<String, String> properties = new LinkedMultiValueMap<>();
        properties.addAll("propertiesToBeIncluded", ObjectCreatorUtility.getListOfPropertyNames());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wishList/create")
                        .params(properties)
                        .param("wishListName", ObjectCreatorUtility.getWishListName())
                        .param("privacy", ObjectCreatorUtility.getPrivacy())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        //2.Assert that we have data

        MvcResult wishListResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wishList/all")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn();
        assertNotNull(wishListResult);
        String actualResponseBody = wishListResult.getResponse().getContentAsString();
        assertNotNull(actualResponseBody);

        //3.Assert that we don't have any messages to be shown for price change


        //4.Update price
        //5.Assert new price
        //6.Assert that we have messages to be shown
        //7.Assert that flag is false and we don't have any messages to be shown
    }

    private Airbnbuser createBnBUser(UserType userType, String email){
        Airbnbuser user = userService.insertUser(UserBean.builder()
                .fname("FNAME")
                .lName("LNAME")
                .email(email)
                .userPassword("***")
                .phone("0733236565")
                .emPhone("0733236565")
                .emCountryCode("ROU")
                .emEmail(email)
                .build());
        if(UserType.GUEST.equals(userType))
            userService.setUserType(UserType.GUEST, user.getId());
        else
            userService.setUserType(UserType.HOST, user.getId());
        return user;
    }

    private void createProperty(String propertyName, String email){
        propertyService.createProperty(PropertyBean
                .builder()
                .zipcode(123)
                .hostEmail(email)
                .propertyName(propertyName)
                .build());
    }
}
