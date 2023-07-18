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
public class BedroomId implements Serializable {
    private static final long serialVersionUID = -2289440958143160030L;
    @NotNull
    @Column(name = "PropertyID", nullable = false)
    private Integer propertyID;

    @Size(max = 5)
    @NotNull
    @Column(name = "BedroomNumber", nullable = false, length = 5)
    private String bedroomNumber;

    @Size(max = 10)
    @NotNull
    @Column(name = "BedType", nullable = false, length = 10)
    private String bedType;

    @NotNull
    @Column(name = "BedCnt", nullable = false)
    private Integer bedCnt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BedroomId entity = (BedroomId) o;
        return Objects.equals(this.bedCnt, entity.bedCnt) &&
                Objects.equals(this.bedroomNumber, entity.bedroomNumber) &&
                Objects.equals(this.propertyID, entity.propertyID) &&
                Objects.equals(this.bedType, entity.bedType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bedCnt, bedroomNumber, propertyID, bedType);
    }

}