package com.reply.airbnbdemo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.airbnbdemo.AirBnbDemoApplication;
import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.dto.PropertyDto;
import com.reply.airbnbdemo.enums.UserType;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.utils.ObjectCreatorUtility;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AirBnbDemoApplication.class)
@AutoConfigureMockMvc
public class WishListIntegrationNoContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    private ObjectMapper objectMapper;

    private static JdbcTemplate jdbcTemplate;

    private static final String EMAIL = "test@gmail.com";
    private static final String EMAIL_HOST = "test_H@gmail.com";

    @BeforeEach
    public void setUp(){
     createBnBUser(UserType.GUEST, EMAIL);
     createBnBUser(UserType.HOST, EMAIL_HOST);
     ObjectCreatorUtility.getListOfPropertyNames().forEach(propertyName -> createProperty(propertyName, EMAIL_HOST));

    }

    @Test
    @DisplayName("Test wishlist flow")
    @WithMockUser(value = "test@gmail.com")
    @Rollback
    public void testWishListFlow() throws Exception {
        //1. Create wishlist
        MultiValueMap<String, String> properties = new LinkedMultiValueMap<>();
        properties.addAll("propertiesToBeIncluded", ObjectCreatorUtility.getListOfPropertyNames());

        String wishListName = ObjectCreatorUtility.getWishListName();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wishList/create")
                        .params(properties)
                        .param("wishListName",wishListName)
                        .param("privacy", ObjectCreatorUtility.getPrivacy())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wishList/properties")
                        .params(properties)
                        .param("wishListName", wishListName)
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
        MvcResult messagesResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/propertyUpdated")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertNotNull(messagesResult);
        String actualMessageResponseBody = messagesResult.getResponse().getContentAsString();
        assertNotNull(actualMessageResponseBody);
        List<String> res = objectMapper.readValue(actualMessageResponseBody, new TypeReference<List<String>>() {});
        assertNotNull(res);
        assertEquals(0, res.size());

        //4.Update price

        MvcResult propertyBeforePriceChangeResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/property/byName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("propertyName", ObjectCreatorUtility.PROPERTY_NAME_1)
                        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BigDecimal pricePerNightBefore = objectMapper.readValue(propertyBeforePriceChangeResult.getResponse().getContentAsString(), PropertyDto.class).getPricePerNight();

        MvcResult priceResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/property/updatePrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("propertyName", ObjectCreatorUtility.PROPERTY_NAME_1)
                        .param("newPrice", "200"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        assertNotNull(priceResult);

        //5.Assert new price

        MvcResult propertyAfterPriceChangeResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/property/byName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("propertyName", ObjectCreatorUtility.PROPERTY_NAME_1)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BigDecimal pricePerNightAfter = objectMapper.readValue(propertyAfterPriceChangeResult.getResponse().getContentAsString(), PropertyDto.class).getPricePerNight();

        assertNotEquals(pricePerNightBefore.intValue(), pricePerNightAfter.intValue());
        assertEquals(200, pricePerNightAfter.intValue());

        //6.Assert that we have messages to be shown

        MvcResult messagesAfterResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/propertyUpdated")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertNotNull(messagesAfterResult);
        String actualAfterMessageResponseBody = messagesAfterResult.getResponse().getContentAsString();
        assertNotNull(actualAfterMessageResponseBody);
        List<String> result = objectMapper.readValue(actualAfterMessageResponseBody, new TypeReference<List<String>>() {});
        assertNotNull(result);
        assertEquals(1, result.size());

        //7.Assert that flag is false and we don't have any messages to be shown

        MvcResult message = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/propertyUpdated")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertNotNull(message);
        String messageContent = message.getResponse().getContentAsString();
        assertNotNull(messageContent);
        List<String> resultMessageContent = objectMapper.readValue(messageContent, new TypeReference<List<String>>() {});
        assertNotNull(resultMessageContent);
        assertEquals(0, resultMessageContent.size());
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
                .pricePerNight(new BigDecimal(100))
                .hostEmail(email)
                .propertyName(propertyName)
                .build());
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @AfterAll
    public static void rollback(){
        jdbcTemplate.execute("delete from airbnbuser where Email = '"+ EMAIL +"' OR Email = '"+ EMAIL_HOST +"'");
    }
}
