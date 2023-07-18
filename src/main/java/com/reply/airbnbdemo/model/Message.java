package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.MessageId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message {
    @EmbeddedId
    private MessageId id;

    @MapsId("hostUID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "HostUID", nullable = false)
    private Host hostUID;

    @MapsId("guestID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "GuestID", nullable = false)
    private Guest guestID;

    @NotNull
    @Column(name = "Created", nullable = false)
    private LocalDate created;

    @NotNull
    @Column(name = "Message_To", nullable = false)
    private Integer messageTo;

    @NotNull
    @Column(name = "Message_From", nullable = false)
    private Integer messageFrom;

    @Size(max = 1000)
    @Column(name = "Body", length = 1000)
    private String body;

}