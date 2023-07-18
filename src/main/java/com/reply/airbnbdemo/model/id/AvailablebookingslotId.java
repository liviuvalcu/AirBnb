package com.reply.airbnbdemo.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AvailablebookingslotId implements Serializable {
    private static final long serialVersionUID = -2027143669135446923L;

    @NotNull
    @Column(name = "PID", nullable = false)
    private Integer pid;

    @NotNull
    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AvailablebookingslotId entity = (AvailablebookingslotId) o;
        return Objects.equals(this.endDate, entity.endDate) &&
                Objects.equals(this.pid, entity.pid) &&
                Objects.equals(this.startDate, entity.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endDate, pid, startDate);
    }

}