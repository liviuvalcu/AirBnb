package com.reply.airbnbdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "booking")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @Column(name = "BID", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "CheckInDate", nullable = false)
    private LocalDate checkInDate;

    @NotNull
    @Column(name = "CheckOutDate", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "AmountPaid", precision = 6, scale = 2)
    private BigDecimal amountPaid;

    @NotNull
    @Column(name = "BookingDate", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "ModifiedDate")
    private LocalDate modifiedDate;

    @Column(name = "SeniorGuestNum")
    private Integer seniorGuestNum;

    @Column(name = "AdultGuestNum")
    private Integer adultGuestNum;

    @Column(name = "ChildGuestNum")
    private Integer childGuestNum;

    @Column(name = "IsCancelled")
    private Character isCancelled;

    @Column(name = "RefundPaid")
    private Character refundPaid;

    @Column(name = "CancelDate")
    private LocalDate cancelDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PromoCode")
    private Promo promoCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "GuestUID")
    private Guest guestUID;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PID")
    private Propertylisting pid;

    @Column(name = "TotalPrice", precision = 6, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "Tax", precision = 4, scale = 2)
    private BigDecimal tax;

    @Column(name = "TotalPriceWTax", precision = 6, scale = 2)
    private BigDecimal totalPriceWTax;

    @Column(name = "AmountDue", precision = 4, scale = 2)
    private BigDecimal amountDue;

    @Column(name = "RefundAmt", precision = 4, scale = 2)
    private BigDecimal refundAmt;

}