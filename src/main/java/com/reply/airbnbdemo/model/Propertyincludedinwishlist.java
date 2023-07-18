package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.PropertyincludedinwishlistId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "propertyincludedinwishlist")
public class Propertyincludedinwishlist {
    @EmbeddedId
    private PropertyincludedinwishlistId id;

    @MapsId("pid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PID", nullable = false)
    private Propertylisting pid;

    @MapsId("airBnBUID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "AirBnBUID", nullable = false)
    private Guest airBnBUID;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)


    @PrimaryKeyJoinColumns(value = {
            @PrimaryKeyJoinColumn(name = "WishlistName", referencedColumnName = "AirBnBUID"),
            @PrimaryKeyJoinColumn(name = "WishlistName", referencedColumnName = "WishlistName")
    })
    private Wishlist wishlistName;

    @Column(name = "CheckInDate")
    private LocalDate checkInDate;

    @Column(name = "CheckOutDate")
    private LocalDate checkOutDate;

}