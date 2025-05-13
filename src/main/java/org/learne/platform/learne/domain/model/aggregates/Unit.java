package org.learne.platform.learne.domain.model.aggregates;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.learne.platform.learne.domain.model.commands.Unit.CreateUnitCommand;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.List;

@Entity
@Getter
public class Unit extends AuditableAbstractAggregateRoot<Unit> {

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course courses;
    @Column(nullable = false)
    private String title;

    public Unit() {}

    public Unit(CreateUnitCommand command) {
        this.courses = new Course(command.courseId());
        this.title = command.title();
    }

    public Unit(Long id) {
        this.setId(id);
    }
}
