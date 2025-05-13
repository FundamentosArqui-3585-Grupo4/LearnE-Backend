package org.learne.platform.learne.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.learne.domain.model.commands.CoursesEnrollment.CreateCoursesEnrollmentCommand;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
public class CoursesEnrollment extends AuditableAbstractAggregateRoot<CoursesEnrollment> {
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public CoursesEnrollment() {}

    public CoursesEnrollment(CreateCoursesEnrollmentCommand command) {
        this.user = new User(command.studentId());
        this.course = new Course(command.courseId());
    }
}
