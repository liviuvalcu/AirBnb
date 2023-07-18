package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.PropertyphotoId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "propertyphoto")
public class Propertyphoto {
    @EmbeddedId
    private PropertyphotoId id;

    @MapsId("pid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PID", nullable = false)
    private Propertylisting pid;

    @NotNull
    @Column(name = "ImageFile", nullable = false)
    private byte[] imageFile;

}