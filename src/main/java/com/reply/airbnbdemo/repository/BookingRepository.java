package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = """
    select count(b.BID) from booking b join propertylisting pl ON b.pid = pl.PID 
     where pl.PropertyName = :propertyName AND
        (b.CheckInDate = :checkinDate OR b.CheckInDate < :checkinDate) AND b.CheckOutDate > :checkinDate""", nativeQuery = true)
    long checkIfCanBeBooked(@Param("checkinDate") LocalDate checkinDate, @Param("propertyName") String propertyName);

    @Query(value = "select max(b.id) from Booking b")
    Integer getMaxId();


}
