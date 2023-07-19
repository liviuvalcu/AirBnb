package com.reply.airbnbdemo.repository.auth;

import com.reply.airbnbdemo.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String username);
}
