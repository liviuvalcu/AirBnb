package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.model.Propertyincludedinwishlist;
import com.reply.airbnbdemo.model.Propertylisting;
import com.reply.airbnbdemo.model.Wishlist;
import com.reply.airbnbdemo.model.id.PropertyincludedinwishlistId;
import com.reply.airbnbdemo.repository.PropertyIncludedInWishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyIncludedInWishListService {

    private final PropertyIncludedInWishListRepository propertyIncludedInWishListRepository;


    public void addPropertyInWishList(Wishlist wishlist, List<Propertylisting> properties){

        properties.stream().forEach(property -> {
            PropertyincludedinwishlistId id = PropertyincludedinwishlistId
                    .builder()
                    .airBnBUID(wishlist.getAirBnBUID().getId())
                    .wishlistName(wishlist.getId().getWishlistName())
                    .pid(property.getId())
                    .build();
            Propertyincludedinwishlist propertyincludedinwishlist = Propertyincludedinwishlist.builder().id(id).pid(property).wishlistName(wishlist).build();
            propertyIncludedInWishListRepository.saveAndFlush(propertyincludedinwishlist);
        });

    }

    public void addNewProperties(Wishlist wishlist, List<Propertylisting> properties){

        properties.stream().forEach(property -> {
            PropertyincludedinwishlistId id = PropertyincludedinwishlistId
                    .builder()
                    .pid(property.getId())
                    .airBnBUID(wishlist.getAirBnBUID().getId())
                    .wishlistName(wishlist.getId().getWishlistName())
                    .build();
            Propertyincludedinwishlist propertyincludedinwishlist = propertyIncludedInWishListRepository.getReferenceById(id);
            propertyIncludedInWishListRepository.saveAndFlush(propertyincludedinwishlist);
        });

    }

    @Transactional
    public void updateChangedFlag(Integer propertyId){
        propertyIncludedInWishListRepository.updateModifiedFlag(propertyId);
    }

}
