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
public class PropertyphotoId implements Serializable {
    private static final long serialVersionUID = -8826279147365331668L;
    @NotNull
    @Column(name = "PID", nullable = false)
    private Integer pid;

    @Size(max = 20)
    @NotNull
    @Column(name = "PropertyName", nullable = false, length = 20)
    private String propertyName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyphotoId entity = (PropertyphotoId) o;
        return Objects.equals(this.propertyName, entity.propertyName) &&
                Objects.equals(this.pid, entity.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyName, pid);
    }

}