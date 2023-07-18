package com.reply.airbnbdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "promo")
public class Promo {
    @Id
    @Size(max = 10)
    @Column(name = "Code", nullable = false, length = 10)
    private String id;

    @NotNull
    @Column(name = "Discount_Amt", nullable = false)
    private Integer discountAmt;

    @OneToMany(mappedBy = "promoCode")
    private Set<Booking> bookings = new LinkedHashSet<>();

}