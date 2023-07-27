package com.reply.airbnbdemo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;


@Data
public class WishlistDto {

    @NotNull
    private final Integer idAirBnBUID;

    @Size(max = 50)
    @NotNull
    private final String idWishlistName;

    private final Character privacy;
}