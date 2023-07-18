package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.ReviewforpropertyId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "reviewforproperty")
public class Reviewforproperty {
    @EmbeddedId
    private ReviewforpropertyId id;

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
    @Column(name = "Created_Time", nullable = false)
    private LocalDate createdTime;

    @Column(name = "Modified_Time")
    private LocalDate modifiedTime;

    @Size(max = 1000)
    @Column(name = "CommentInReview", length = 1000)
    private String commentInReview;

    @Column(name = "Cleanliness_Rating", precision = 2, scale = 1)
    private BigDecimal cleanlinessRating;

    @Column(name = "Communication_Rating", precision = 2, scale = 1)
    private BigDecimal communicationRating;

    @Column(name = "CheckIn_Rating", precision = 2, scale = 1)
    private BigDecimal checkinRating;

    @Column(name = "Accuracy_Rating", precision = 2, scale = 1)
    private BigDecimal accuracyRating;

    @Column(name = "Location_Rating", precision = 2, scale = 1)
    private BigDecimal locationRating;

    @Column(name = "Value_Rating", precision = 2, scale = 1)
    private BigDecimal valueRating;

    @Column(name = "Overall_Rating", precision = 2, scale = 1)
    private BigDecimal overallRating;

}