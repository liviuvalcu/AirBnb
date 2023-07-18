package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Creditcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<Creditcard, Integer> {
}
