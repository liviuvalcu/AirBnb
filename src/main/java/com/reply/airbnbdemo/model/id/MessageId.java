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
public class MessageId implements Serializable {
    private static final long serialVersionUID = -4392678221470796710L;
    @NotNull
    @Column(name = "HostUID", nullable = false)
    private Integer hostUID;

    @NotNull
    @Column(name = "GuestID", nullable = false)
    private Integer guestID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MessageId entity = (MessageId) o;
        return Objects.equals(this.hostUID, entity.hostUID) &&
                Objects.equals(this.guestID, entity.guestID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostUID, guestID);
    }

}