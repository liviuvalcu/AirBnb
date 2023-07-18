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
public class HouseruleId implements Serializable {
    private static final long serialVersionUID = 2230164709940652864L;
    @NotNull
    @Column(name = "PID", nullable = false)
    private Integer pid;

    @Size(max = 20)
    @NotNull
    @Column(name = "RuleName", nullable = false, length = 20)
    private String ruleName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HouseruleId entity = (HouseruleId) o;
        return Objects.equals(this.ruleName, entity.ruleName) &&
                Objects.equals(this.pid, entity.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleName, pid);
    }

}