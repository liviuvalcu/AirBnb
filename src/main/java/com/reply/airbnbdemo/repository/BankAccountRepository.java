package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Bankaccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankAccountRepository extends JpaRepository<Bankaccount, Integer> {

    @Query(value = "select coalesce(max(ba.AccountNUMBER), 1) from bankaccount ba", nativeQuery = true)
    Integer getIdAndIncrement();
}
