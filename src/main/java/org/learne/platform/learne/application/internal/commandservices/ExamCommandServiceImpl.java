package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.Exam;
import org.learne.platform.learne.domain.model.commands.Exam.CreateExamCommand;
import org.learne.platform.learne.domain.services.Exam.ExamCommandService;
import org.learne.platform.learne.infrastructure.persistence.jpa.ExamRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamCommandServiceImpl implements ExamCommandService {

    private final ExamRepository examRepository;

    public ExamCommandServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Long handle(CreateExamCommand command) {
        if(examRepository.existsByCourseIdAndTitle(command.courseId(), command.title())) {
            throw new IllegalArgumentException("Exam in the course" +
                    command.courseId() + "with title " + command.title() + " already exists");
        }

        var newExam = new Exam(command);

        try {
            examRepository.save(newExam);
        }catch (RuntimeException e) {
            throw new IllegalArgumentException("An error occurred while saving exam" + e.getMessage());
        }

        return newExam.getId();
    }
}
