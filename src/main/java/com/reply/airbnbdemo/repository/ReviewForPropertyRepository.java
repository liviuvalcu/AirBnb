package com.reply.airbnbdemo.repository;

import com.reply.airbnbdemo.model.Reviewforproperty;
import com.reply.airbnbdemo.model.id.ReviewforpropertyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewForPropertyRepository extends JpaRepository<Reviewforproperty, ReviewforpropertyId> {
}
