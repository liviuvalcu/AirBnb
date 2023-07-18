package com.reply.airbnbdemo.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PhotoforpropertyreviewId implements Serializable {
    private static final long serialVersionUID = -3424044306673813184L;
    @NotNull
    @Column(name = "GuestID", nullable = false)
    private Integer guestID;

    @NotNull
    @Column(name = "PID", nullable = false)
    private Integer pid;

    @Size(max = 50)
    @NotNull
    @Column(name = "PhotoName", nullable = false, length = 50)
    private String photoName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PhotoforpropertyreviewId entity = (PhotoforpropertyreviewId) o;
        return Objects.equals(this.pid, entity.pid) &&
                Objects.equals(this.photoName, entity.photoName) &&
                Objects.equals(this.guestID, entity.guestID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, photoName, guestID);
    }

}