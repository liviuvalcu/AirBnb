package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
}
