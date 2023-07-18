package com.reply.airbnbdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "creditcard")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Creditcard {
    @Id
    @Column(name = "CardNum", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "CSV", nullable = false)
    private Integer csv;

    @NotNull
    @Column(name = "ExpirationDate", nullable = false)
    private LocalDate expirationDate;

    @Size(max = 50)
    @NotNull
    @Column(name = "CardholderName", nullable = false, length = 50)
    private String cardholderName;

    @Size(max = 6)
    @NotNull
    @Column(name = "CardType", nullable = false, length = 6)
    private String cardType;

    @Size(max = 100)
    @Column(name = "Address", length = 100)
    private String address;

    @OneToOne(mappedBy = "creditCardNum")
    private Guest guest;

}