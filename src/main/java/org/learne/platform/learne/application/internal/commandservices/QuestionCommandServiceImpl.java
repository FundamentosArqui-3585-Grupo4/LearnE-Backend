package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.Exam;
import org.learne.platform.learne.domain.model.aggregates.Question;
import org.learne.platform.learne.domain.model.commands.Question.CreateQuestionCommand;
import org.learne.platform.learne.domain.services.Question.QuestionCommandService;
import org.learne.platform.learne.infrastructure.persistence.jpa.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionCommandServiceImpl implements QuestionCommandService {
    private final QuestionRepository questionRepository;

    public QuestionCommandServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Long handle(CreateQuestionCommand command) {
        if(questionRepository.existsByExamIdAndQuestion(command.examId(), command.question())) {
            throw new IllegalArgumentException("Question in the exam" +
                    command.examId() + "with name " + command.question() + " already exists");
        }


        var newQuestion = new Question(command);

        try {
            questionRepository.save(newQuestion);
        }catch (RuntimeException e) {
            throw new IllegalArgumentException("An error occurred while saving question" + e.getMessage());
        }

        return newQuestion.getId();
    }
}
