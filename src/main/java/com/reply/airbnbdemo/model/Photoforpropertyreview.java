package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.PhotoforpropertyreviewId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "photoforpropertyreview")
public class Photoforpropertyreview {
    @EmbeddedId
    private PhotoforpropertyreviewId id;

    @MapsId("guestID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "GuestID", nullable = false)
    private Guest guestID;

    @MapsId("pid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PID", nullable = false)
    private Propertylisting pid;

    @NotNull
    @Column(name = "ImageFile", nullable = false)
    private byte[] imageFile;

}