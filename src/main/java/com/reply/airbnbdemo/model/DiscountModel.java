package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.enums.DiscountLevels;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "discount")
public class DiscountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer minimNights;
    private BigDecimal minimalAmountSpent;
    @Enumerated(EnumType.STRING)
    private DiscountLevels discountLevels;
    private Integer discount;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdDate;
    @LastModifiedDate
    private Instant updatedDate;

}
