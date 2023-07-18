package com.reply.airbnbdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "bankaccount")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bankaccount {
    @Id
    @Column(name = "AccountNUMBER", nullable = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "RoutingNum", nullable = false)
    private Integer routingNum;

    @Size(max = 20)
    @NotNull
    @Column(name = "AccountType", nullable = false, length = 20)
    private String accountType;

    @OneToOne(mappedBy = "bankAccountNumber")
    private Host host;

}