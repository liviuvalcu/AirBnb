package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Propertylisting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Propertylisting, Integer> {

    @Query(value = "select property from Propertylisting property where property.hid.airbnbuser.email = :userEmail or property.country = :country")
    List<Propertylisting> getAllByUserEmailOrCountry(String userEmail, String country);

    @Query(value = "select max(pl.id) from Propertylisting pl")
    Integer getMaxId();

    Propertylisting getByPropertyName(String propertyName);
}