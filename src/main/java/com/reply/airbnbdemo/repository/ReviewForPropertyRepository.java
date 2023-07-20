package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Reviewforproperty;
import com.reply.airbnbdemo.model.id.ReviewforpropertyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository
public interface ReviewForPropertyRepository extends JpaRepository<Reviewforproperty, ReviewforpropertyId> {

    @Query(value = "SELECT AVG(r.overallRating) FROM Reviewforproperty r")
    BigDecimal getAvgRating();
}
