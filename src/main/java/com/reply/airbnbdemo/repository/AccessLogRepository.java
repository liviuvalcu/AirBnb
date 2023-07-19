package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.Accesslog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<Accesslog, Integer>{
}
