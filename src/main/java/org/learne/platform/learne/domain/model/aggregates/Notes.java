package org.learne.platform.learne.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.learne.domain.model.commands.Note.CreateNotesCommand;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter

public class Notes extends AuditableAbstractAggregateRoot<Notes> {

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User studentId;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam examId;

    @Column(nullable = false)
    private Float note;


    public Notes() {}

    public Notes(CreateNotesCommand command) {
      this.studentId=new User(command.studentId());
      this.examId=new Exam(command.examId());
      this.note=command.note();
    }

    public Notes( Long id) {
        this.setId(id);
    }
}
