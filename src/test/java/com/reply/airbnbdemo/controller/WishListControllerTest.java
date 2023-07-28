package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.TestConfig;
import com.reply.airbnbdemo.service.WishListService;
import com.reply.airbnbdemo.utils.ObjectCreatorUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = {WishListController.class}, useDefaultFilters = false)
@Import({WishListController.class, TestConfig.class})
class WishListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private WishListService wishListService;


    @Test
    @WithMockUser(value = "test@gmail.com")
    void createWishList() throws Exception {
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
    }

    @Test
    @WithMockUser(value = "test@gmail.com")
    void updateWishList() throws Exception {
        MultiValueMap<String, String> properties = new LinkedMultiValueMap<>();
        properties.addAll("propertiesToBeIncluded", ObjectCreatorUtility.getListOfPropertyNames());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wishList/update")
                        .params(properties)
                        .param("wishListName", ObjectCreatorUtility.getWishListName())
                        .param("privacy", ObjectCreatorUtility.getPrivacy())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithMockUser(value = "test@gmail.com")
    void addPropertiesWishList() throws Exception {
        MultiValueMap<String, String> properties = new LinkedMultiValueMap<>();
        properties.addAll("propertiesToBeIncluded", ObjectCreatorUtility.getListOfPropertyNames());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wishList/addProperties")
                        .params(properties)
                        .param("wishListName", ObjectCreatorUtility.getWishListName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithMockUser(value = "test@gmail.com")
    void deleteWishList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/wishList/delete")
                        .param("wishListName", ObjectCreatorUtility.getWishListName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithMockUser(value = "test@gmail.com")
    void getAllWishLists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wishList/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

}