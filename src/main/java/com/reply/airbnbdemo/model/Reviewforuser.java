package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.ReviewforuserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reviewforusers")
public class Reviewforuser {
    @EmbeddedId
    private ReviewforuserId id;

    @MapsId("hostUID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "HostUID", nullable = false)
    private Host hostUID;

    @MapsId("guestUID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "GuestUID", nullable = false)
    private Guest guestUID;

    @Column(name = "GuestRating", precision = 2, scale = 1)
    private BigDecimal guestRating;

    @Column(name = "HostRating", precision = 2, scale = 1)
    private BigDecimal hostRating;

    @Size(max = 1000)
    @Column(name = "CommentForHost", length = 1000)
    private String commentForHost;

    @Size(max = 1000)
    @Column(name = "CommentForGuest", length = 1000)
    private String commentForGuest;

    @Column(name = "ReviewForHostCreated")
    private LocalDate reviewForHostCreated;

    @Column(name = "ReviewForGuestCreated")
    private LocalDate reviewForGuestCreated;

    @Column(name = "ReviewForHostModified")
    private LocalDate reviewForHostModified;

    @Column(name = "ReviewForGuestModified")
    private LocalDate reviewForGuestModified;

}