package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Integer> {
}
