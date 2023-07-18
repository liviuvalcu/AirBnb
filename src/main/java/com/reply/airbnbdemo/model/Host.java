package com.reply.airbnbdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "host")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Host {
    @Id
    @Column(name = "AirBnBUID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "AirBnBUID", nullable = false)
    private Airbnbuser airbnbuser;

    @Column(name = "IsSuperHost")
    private Character isSuperHost;

    @Column(name = "AvgRating", precision = 2, scale = 1)
    private BigDecimal avgRating;

    @Column(name = "NumOfRatings")
    private Integer numOfRatings;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "BankAccountNumber", nullable = false)
    private Bankaccount bankAccountNumber;

    @OneToMany(mappedBy = "hostUID")
    private Set<Message> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "hid")
    private Set<Propertylisting> propertylistings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "hostUID")
    private Set<Reviewforuser> reviewforusers = new LinkedHashSet<>();

}