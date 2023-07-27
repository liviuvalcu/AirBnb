package com.reply.airbnbdemo.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class PropertyincludedinwishlistId implements Serializable {
    private static final long serialVersionUID = -7076287581716111206L;
    @NotNull
    @Column(name = "PID", nullable = false)
    private Integer pid;

    @NotNull
    @Column(name = "AirBnBUID", nullable = false)
    private Integer airBnBUID;

    @Size(max = 50)
    @NotNull
    @Column(name = "WishlistName", nullable = false, length = 50)
    private String wishlistName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyincludedinwishlistId entity = (PropertyincludedinwishlistId) o;
        return Objects.equals(this.airBnBUID, entity.airBnBUID) &&
                Objects.equals(this.pid, entity.pid) &&
                Objects.equals(this.wishlistName, entity.wishlistName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airBnBUID, pid, wishlistName);
    }

}