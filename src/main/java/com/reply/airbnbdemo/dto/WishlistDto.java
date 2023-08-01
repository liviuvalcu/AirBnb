package com.reply.airbnbdemo.dto;

import com.reply.airbnbdemo.model.Propertyincludedinwishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {

    private Integer idAirBnBUID;
    private String idWishlistName;
    private Character privacy;
    private Set<Propertyincludedinwishlist> properties;
}