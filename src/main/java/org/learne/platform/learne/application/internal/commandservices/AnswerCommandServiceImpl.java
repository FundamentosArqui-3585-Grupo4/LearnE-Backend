package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.Answer;
import org.learne.platform.learne.domain.model.commands.Answer.CreateAnswerCommand;
import org.learne.platform.learne.domain.services.Answer.AnswerCommandService;
import org.learne.platform.learne.infrastructure.persistence.jpa.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerCommandServiceImpl implements AnswerCommandService {

    private final AnswerRepository answerRepository;

    public AnswerCommandServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }


    @Override
    public Long handle(CreateAnswerCommand command) {

        if(answerRepository.existsByQuestionIdAndDescription(command.questionId(),
                command.description())) {
            throw new IllegalArgumentException("This answer already exists in the question" + command.questionId());
        }

        if(answerRepository.existsByQuestionIdAndIsCorrect(command.questionId(), command.isCorrect())) {
            throw new IllegalArgumentException("A correct answer already exists in the question" + command.questionId());
        }

        var newAnswer = new Answer(command);

        try {
            answerRepository.save(newAnswer);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("An error occurred while saving answer" + e.getMessage());
        }

        return newAnswer.getId();
    }
}
