package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.dto.WishlistDto;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.model.Wishlist;
import com.reply.airbnbdemo.model.id.WishlistId;
import com.reply.airbnbdemo.repository.WishListRepository;
import com.reply.airbnbdemo.utils.ObjectCreatorUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishListServiceTest {

    @InjectMocks
    private WishListService wishListService;


    @Mock private WishListRepository wishListRepository;
    @Mock private PropertyIncludedInWishListService propertyIncludedInWishListService;
    @Mock private UserService userService;
    @Mock private PropertyService propertyService;
    @Mock private  ModelMapper modelMapper;

    @Test
    void createWishList() {
        String userEmail = ObjectCreatorUtility.getUserEmail();
        String wishlistName = ObjectCreatorUtility.getWishListName();
        String privacy = ObjectCreatorUtility.getPrivacy();
        List<String> includedProperties = ObjectCreatorUtility.getListOfPropertyNames();

        when(userService.findByEmailEntity(userEmail)).thenReturn(Airbnbuser.builder().build());


        wishListService.createWishList(userEmail, wishlistName, privacy, includedProperties);
    }

    @Test
    void updateWishList() {

        String userEmail = ObjectCreatorUtility.getUserEmail();
        String wishlistName = ObjectCreatorUtility.getWishListName();
        String privacy = ObjectCreatorUtility.getPrivacy();
        List<String> includedProperties = ObjectCreatorUtility.getListOfPropertyNames();

        when(userService.findByEmailEntity(userEmail)).thenReturn(Airbnbuser.builder().id(1).build());
        when(wishListRepository.findById(any(WishlistId.class))).thenReturn(Optional.of(Wishlist.builder().id(WishlistId.builder().build()).build()));

        wishListService.updateWishList(userEmail, wishlistName, privacy, includedProperties);
    }

    @Test
    void addNewProperties() {

        String userEmail = ObjectCreatorUtility.getUserEmail();
        String wishlistName = ObjectCreatorUtility.getWishListName();
        List<String> includedProperties = ObjectCreatorUtility.getListOfPropertyNames();

        when(userService.findByEmailEntity(userEmail)).thenReturn(Airbnbuser.builder().id(1).build());
        when(wishListRepository.findById(any(WishlistId.class))).thenReturn(Optional.of(Wishlist.builder().id(WishlistId.builder().build()).build()));

        wishListService.addNewProperties(userEmail, wishlistName, includedProperties);
    }

    @Test
    void deleteWishList() {

        String userEmail = ObjectCreatorUtility.getUserEmail();
        String wishlistName = ObjectCreatorUtility.getWishListName();
        when(userService.findByEmailEntity(userEmail)).thenReturn(Airbnbuser.builder().id(1).build());

        wishListService.deleteWishList(wishlistName,userEmail);
    }

    @Test
    void getAll() {
        String userEmail = ObjectCreatorUtility.getUserEmail();

        when(wishListRepository.findAllByUserName(userEmail)).thenReturn(List.of(Wishlist.builder().build()));
        when(modelMapper.map(any(), any())).thenReturn(WishlistDto.builder().build());
        List<WishlistDto> dtos = wishListService.getAll(userEmail);
        assertNotNull(dtos);
        assertEquals(1, dtos.size());
    }

    @Test
    void getAllEntity() {
        String userEmail = ObjectCreatorUtility.getUserEmail();

        when(wishListRepository.findAllByUserName(userEmail)).thenReturn(List.of(Wishlist.builder().build(), Wishlist.builder().build()));
        List<Wishlist> wishList = wishListService.getAllEntity(userEmail);
        assertNotNull(wishList);
        assertEquals(2, wishList.size());
    }
}