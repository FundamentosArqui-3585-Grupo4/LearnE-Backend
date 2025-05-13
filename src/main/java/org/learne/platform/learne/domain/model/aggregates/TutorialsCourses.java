package org.learne.platform.learne.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.learne.domain.model.commands.TutorialsCourses.CreateTutorialsCoursesCommand;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class TutorialsCourses extends AuditableAbstractAggregateRoot<TutorialsCourses> {
    @ManyToOne
    @JoinColumn(name = "courses_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String hour;

    @Column
    private Boolean isReservated = false;

    @Column
    private String link;

    public TutorialsCourses() {}

    public TutorialsCourses updateTutorialsCourse(Long course, Long user,
                                                  String date, String hour, Boolean isReserved, String link) {
        this.course = new Course(course);
        this.user = new User(user);
        this.date = date;
        this.hour = hour;
        this.isReservated = isReserved;
        this.link = link;
        return this;
    }

    public TutorialsCourses(CreateTutorialsCoursesCommand command) {
        this.course = new Course(command.courseId());
        this.user = new User(command.teacherId());
        this.date = command.date();
        this.hour = command.hour();
        this.isReservated = command.isReservated();
        this.link = command.link();
    }

    public TutorialsCourses(Long courseId, Long teacherId, String date, String hour, Boolean isReservated, String link) {
        this.course = new Course(courseId);
        this.user = new User(teacherId);
        this.date = date;
        this.hour = hour;
        this.isReservated = isReservated;
        this.link = link;
    }


    public TutorialsCourses(Long id) {this.setId(id);}
}
