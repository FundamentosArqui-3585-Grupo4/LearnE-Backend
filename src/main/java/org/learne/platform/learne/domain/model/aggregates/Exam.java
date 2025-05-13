package org.learne.platform.learne.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.learne.platform.learne.domain.model.commands.Exam.CreateExamCommand;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class Exam extends AuditableAbstractAggregateRoot<Exam> {

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;


    public Exam() {}

    public Exam(CreateExamCommand command) {
        this.unit = new Unit(command.unitId());
        this.course = new Course(command.courseId());
        this.title = command.title();
    }

    public Exam(Long id) {
        this.setId(id);
    }

    public void setTitle(String testExam) {
        if (testExam == null || testExam.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = testExam;
    }
}
