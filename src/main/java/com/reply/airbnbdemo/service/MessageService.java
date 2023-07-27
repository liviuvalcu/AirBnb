package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.model.Propertyincludedinwishlist;
import com.reply.airbnbdemo.model.Wishlist;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final WishListService wishListService;
    private final PropertyIncludedInWishListService propertyIncludedInWishListService;

    public List<String> checkIfPropertyHasChanged(String userName){

        List<Wishlist> userWishLists = wishListService.getAllEntity(userName);
        List<String> updatedProperties = new ArrayList<>();

        userWishLists.forEach(wishlist -> {
            Set<Propertyincludedinwishlist> propertyIncludedInWishLists = wishlist.getPropertyincludedinwishlists();
            propertyIncludedInWishLists.stream().forEach(property -> {
                if(property.getFlag()){
                    updatedProperties.add(property.getPid().getPropertyName());
                    propertyIncludedInWishListService.updateChangedFlag(property.getId().getPid());
                }
            });
        });

        return  updatedProperties;
    }
}
