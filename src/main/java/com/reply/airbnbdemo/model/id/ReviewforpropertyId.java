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
public class ReviewforpropertyId implements Serializable {
    private static final long serialVersionUID = -8305002154714786184L;
    @NotNull
    @Column(name = "GuestID", nullable = false)
    private Integer guestID;

    @NotNull
    @Column(name = "PID", nullable = false)
    private Integer pid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReviewforpropertyId entity = (ReviewforpropertyId) o;
        return Objects.equals(this.pid, entity.pid) &&
                Objects.equals(this.guestID, entity.guestID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, guestID);
    }

}