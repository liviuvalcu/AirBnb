package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Bankaccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<Bankaccount, Integer> {
}
