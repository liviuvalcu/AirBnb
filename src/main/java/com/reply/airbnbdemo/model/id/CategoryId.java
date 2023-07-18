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
public class CategoryId implements Serializable {
    private static final long serialVersionUID = -463047161277216207L;
    @NotNull
    @Column(name = "PID", nullable = false)
    private Integer pid;

    @Size(max = 20)
    @NotNull
    @Column(name = "categoryName", nullable = false, length = 20)
    private String categoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoryId entity = (CategoryId) o;
        return Objects.equals(this.pid, entity.pid) &&
                Objects.equals(this.categoryName, entity.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, categoryName);
    }

}