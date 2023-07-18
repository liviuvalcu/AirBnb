package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.AvailablebookingslotId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "availablebookingslot")
public class Availablebookingslot {
    @EmbeddedId
    private AvailablebookingslotId id;

    @MapsId("pid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PID", nullable = false)
    private Propertylisting pid;

}