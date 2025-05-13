package org.learne.platform.learne.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.learne.domain.model.commands.CreatedCourseCommand;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Course extends AuditableAbstractAggregateRoot<Course> {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Size(max = 50)
    private String level;

    @Column(nullable = false)
    @Size(max = 50)
    private String duration;

    @Column(nullable = false)
    private String prior_knowledge;

    @Column(nullable = false)
    private String principal_image;

    @Column(nullable = false)
    private String url_video;


    public Course() {}

    public Course(CreatedCourseCommand command) {
        this.title = command.title();
        this.description = command.description();
        this.user = new User(command.teacherId());
        this.level = command.level();
        this.duration = command.duration();
        this.prior_knowledge = command.prior_knowledge();
        this.principal_image = command.principal_image();
        this.url_video = command.url_video();
    }

    public Course(Long id) {
        this.setId(id);
    }

}
