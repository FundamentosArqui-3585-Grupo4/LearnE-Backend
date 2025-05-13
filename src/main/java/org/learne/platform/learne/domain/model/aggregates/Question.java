package org.learne.platform.learne.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.learne.platform.learne.domain.model.commands.Question.CreateQuestionCommand;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
public class Question extends AuditableAbstractAggregateRoot<Question> {

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Column(nullable = false)
    private String question;

    public Question() {}

    public Question(CreateQuestionCommand command) {
        this.exam = new Exam(command.examId());
        this.question = command.question();
    }

    public Question(Long id) {
        this.setId(id);
    }
}
