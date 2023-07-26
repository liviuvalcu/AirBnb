package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = """
    select count(b.BID) from booking b join propertylisting pl ON b.pid = pl.PID 
     where pl.PropertyName = :propertyName AND
        (b.CheckInDate = :checkinDate OR b.CheckInDate < :checkinDate) AND b.CheckOutDate > :checkinDate""", nativeQuery = true)
    long checkIfCanBeBooked(@Param("checkinDate") LocalDate checkinDate, @Param("propertyName") String propertyName);

    @Query(value = "select max(b.id) from Booking b")
    Integer getMaxId();

    @Query(value = "select SUM(b.amountPaid) from Booking b where b.guestUID.airbnbuser.email = :email")
    Integer countAmountSpentByUser(@Param("email") String email);

    @Query(value = "select SUM(FUNCTION('datediff', b.checkOutDate, b.checkInDate)) from Booking b where b.guestUID.airbnbuser.email = :email")
    Integer countNightsStayed(@Param("email")String email);


    @Query(value = "select b from Booking b where b.pid.propertyName = :propertyName")
    List<Booking> findAllByPropertyName(@Param("propertyName") String propertyName);

}
