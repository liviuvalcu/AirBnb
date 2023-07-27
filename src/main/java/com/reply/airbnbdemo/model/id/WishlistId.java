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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WishlistId implements Serializable {
    private static final long serialVersionUID = -269144558419292609L;
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
        WishlistId entity = (WishlistId) o;
        return Objects.equals(this.airBnBUID, entity.airBnBUID) &&
                Objects.equals(this.wishlistName, entity.wishlistName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airBnBUID, wishlistName);
    }

}