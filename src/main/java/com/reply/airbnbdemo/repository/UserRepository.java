package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Airbnbuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Airbnbuser, Integer> {

     Airbnbuser findByEmail(String email);

     @Query(value = "select user from Airbnbuser user where user.lName = :lName and user.fname = :fName")
    Optional<Airbnbuser> findByFnameAndLName(String fName, String lName);


     void deleteAirbnbuserById(Integer id);
}
