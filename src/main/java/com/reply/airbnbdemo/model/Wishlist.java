package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.WishlistId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wishlist")
public class Wishlist {

    @EmbeddedId
    private WishlistId id;

    @MapsId("airBnBUID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "AirBnBUID", nullable = false)
    private Guest airBnBUID;

    @Column(name = "Privacy")
    private Character privacy;

    @OneToMany(mappedBy = "wishlistName")
    private transient Set<Propertyincludedinwishlist> propertyincludedinwishlists = new LinkedHashSet<>();

}