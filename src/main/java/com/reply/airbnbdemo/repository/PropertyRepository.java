package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Propertylisting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface PropertyRepository extends JpaRepository<Propertylisting, Integer> {

    @Query(value = "select property from Propertylisting property where property.hid.airbnbuser.email = :userEmail or property.country = :country")
    List<Propertylisting> getAllByUserEmailOrCountry(String userEmail, String country);

    @Query(value = "select max(pl.id) from Propertylisting pl")
    Integer getMaxId();

    Propertylisting getByPropertyName(String propertyName);

    @Query(value = "SELECT SUM(p.numOfRatings) FROM Propertylisting p")
    Integer getNumberOfRatings();

    @Query(value = "select p from Propertylisting p where p.propertyName IN :propertyNames")
    List<Propertylisting> getAllByNames(@Param("propertyNames") List<String> propertyNames);

    @Query(value = "update Propertylisting p set p.pricePerNight= :newPrice where p.propertyName= :propertyName")
    @Modifying
    void updatePrice(@Param("propertyName") String propertyName, @Param("newPrice")BigDecimal newPrice);

}
