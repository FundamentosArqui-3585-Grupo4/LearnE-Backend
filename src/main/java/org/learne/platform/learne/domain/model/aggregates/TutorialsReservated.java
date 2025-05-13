package org.learne.platform.learne.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.learne.domain.model.commands.CreateTutorialsReservatedCommand;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class TutorialsReservated extends AuditableAbstractAggregateRoot<TutorialsReservated> {
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "tutorial_id", nullable = false)
    private TutorialsCourses tutorialsCourses;

    public TutorialsReservated() {}

    public TutorialsReservated(CreateTutorialsReservatedCommand command) {
        this.user = new User(command.studentId());
        this.tutorialsCourses = new TutorialsCourses(command.tutorialId());
    }
}
