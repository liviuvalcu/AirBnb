package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.BedroomId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "bedroom")
public class Bedroom {
    @EmbeddedId
    private BedroomId id;

    @MapsId("propertyID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PropertyID", nullable = false)
    private Propertylisting propertyID;

}