package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Creditcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CreditCardRepository extends JpaRepository<Creditcard, Integer> {

    @Query(value = "select max(b.id) from Creditcard b")
    Integer getMaxId();
}
