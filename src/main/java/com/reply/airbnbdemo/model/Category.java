package com.reply.airbnbdemo.model;

import com.reply.airbnbdemo.model.id.CategoryId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @EmbeddedId
    private CategoryId id;

    @MapsId("pid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PID", nullable = false)
    private Propertylisting pid;

}