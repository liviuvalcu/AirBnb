package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.dto.WishlistDto;
import com.reply.airbnbdemo.model.Airbnbuser;
import com.reply.airbnbdemo.model.Wishlist;
import com.reply.airbnbdemo.model.id.WishlistId;
import com.reply.airbnbdemo.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;
    private final PropertyIncludedInWishListService propertyIncludedInWishListService;
    private final UserService userService;
    private final PropertyService propertyService;
    private final ModelMapper modelMapper;


    @Transactional
    public Wishlist createWishList(String userEmail, String wishListName, String privacy){
        Airbnbuser user = userService.findByEmailEntity(userEmail);

        WishlistId id = WishlistId.builder().airBnBUID(user.getId()).wishlistName(wishListName).build();
        Wishlist wishlist = Wishlist
                .builder()
                .id(id)
                .airBnBUID(user.getGuest())
                .privacy(privacy.charAt(0))
                .build();

        wishListRepository.saveAndFlush(wishlist);
        return wishlist;
    }

    @Transactional
    public void addPropertiesToWishList(String wishlistName, List<String> propertiesToBeIncluded, String userName){
        propertyIncludedInWishListService.addPropertyInWishList(wishListRepository
                .findAllByUserNameAndWishListName(userName,
                        wishlistName), propertyService.getPropertiesByName(propertiesToBeIncluded));
    }

    public void  updateWishList(String userEmail, String wishListName, String privacy, List<String> propertiesToBeIncluded){
        Airbnbuser user = userService.findByEmailEntity(userEmail);
        Wishlist wishlist = wishListRepository.findById(WishlistId.builder().wishlistName(wishListName).airBnBUID(user.getId()).build()).orElseThrow();
        wishlist.setPrivacy(privacy.charAt(0));
        wishlist.getId().setWishlistName(wishListName);
        wishListRepository.saveAndFlush(wishlist);
        propertyIncludedInWishListService.addNewProperties(wishlist, propertyService.getPropertiesByName(propertiesToBeIncluded));
    }

    public void addNewProperties(String userEmail, String wishListName, List<String> propertiesToBeIncluded){
        Airbnbuser user = userService.findByEmailEntity(userEmail);
        Wishlist wishlist = wishListRepository.findById(WishlistId.builder().wishlistName(wishListName).airBnBUID(user.getId()).build()).orElseThrow();
        propertyIncludedInWishListService.addPropertyInWishList(wishlist, propertyService.getPropertiesByName(propertiesToBeIncluded));
    }

    public void deleteWishList(String wishListName, String userName){
        wishListRepository.deleteById(WishlistId
                .builder()
                .wishlistName(wishListName)
                .airBnBUID(userService.findByEmailEntity(userName).getId())
                .build());
    }

    public List<WishlistDto> getAll(String username){
      return  wishListRepository.findAllByUserName(username).stream().map(wishList -> {
          WishlistDto dto = modelMapper.map(wishList, WishlistDto.class);

          dto.setIdAirBnBUID(wishList.getId().getAirBnBUID());
          dto.setIdWishlistName(wishList.getId().getWishlistName());
          return dto;
      }).collect(Collectors.toList());
    }

    public List<Wishlist> getAllEntity(String username){
        return wishListRepository.findAllByUserName(username);
    }
}
