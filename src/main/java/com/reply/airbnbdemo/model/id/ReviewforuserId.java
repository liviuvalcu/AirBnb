package com.reply.airbnbdemo.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ReviewforuserId implements Serializable {
    private static final long serialVersionUID = -3772546622652325729L;
    @NotNull
    @Column(name = "HostUID", nullable = false)
    private Integer hostUID;

    @NotNull
    @Column(name = "GuestUID", nullable = false)
    private Integer guestUID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReviewforuserId entity = (ReviewforuserId) o;
        return Objects.equals(this.hostUID, entity.hostUID) &&
                Objects.equals(this.guestUID, entity.guestUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostUID, guestUID);
    }

}