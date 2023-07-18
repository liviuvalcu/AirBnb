package com.reply.airbnbdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "propertylisting")
public class Propertylisting {
    @Id
    @Column(name = "PID", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "PropertyName", length = 50)
    private String propertyName;

    @NotNull
    @Column(name = "Zipcode", nullable = false)
    private Integer zipcode;

    @Column(name = "BathroomCnt")
    private Integer bathroomCnt;

    @Column(name = "BedroomCnt")
    private Integer bedroomCnt;

    @Column(name = "GuestNum")
    private Integer guestNum;

    @Column(name = "PricePerNight", precision = 6, scale = 2)
    private BigDecimal pricePerNight;

    @Column(name = "CleaningFee", precision = 4, scale = 2)
    private BigDecimal cleaningFee;

    @Column(name = "Created")
    private LocalDate created;

    @Column(name = "CheckInTime")
    private Instant checkInTime;

    @Column(name = "CheckOutTime")
    private Instant checkOutTime;

    @Column(name = "IsRefundable")
    private Character isRefundable;

    @Column(name = "CancellationPeriod")
    private Integer cancellationPeriod;

    @Size(max = 10)
    @Column(name = "CancellationType", length = 10)
    private String cancellationType;

    @Column(name = "RefundRate", precision = 2, scale = 1)
    private BigDecimal refundRate;

    @Column(name = "NumOfRatings")
    private Integer numOfRatings;

    @Column(name = "AvgRatings", precision = 2, scale = 1)
    private BigDecimal avgRatings;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "HID")
    private Host hid;

    @Size(max = 20)
    @Column(name = "Street", length = 20)
    private String street;

    @Size(max = 20)
    @Column(name = "City", length = 20)
    private String city;

    @Size(max = 20)
    @Column(name = "StateofResidence", length = 20)
    private String stateofResidence;

    @Size(max = 20)
    @Column(name = "Country", length = 20)
    private String country;

    @Column(name = "TaxRate", precision = 2, scale = 1)
    private BigDecimal taxRate;

    @OneToMany(mappedBy = "pid")
    private Set<Availablebookingslot> availablebookingslots = new LinkedHashSet<>();

    @OneToMany(mappedBy = "propertyID")
    private Set<Bedroom> bedrooms = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pid")
    private Set<Photoforpropertyreview> photoforpropertyreviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pid")
    private Set<Propertyincludedinwishlist> propertyincludedinwishlists = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pid")
    private Set<Amenity> amenities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pid")
    private Set<Reviewforproperty> reviewforproperties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pid")
    private Set<Category> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pid")
    private Set<Propertyphoto> propertyphotos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pid")
    private Set<Houserule> houserules = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pid")
    private Set<Booking> bookings = new LinkedHashSet<>();

}