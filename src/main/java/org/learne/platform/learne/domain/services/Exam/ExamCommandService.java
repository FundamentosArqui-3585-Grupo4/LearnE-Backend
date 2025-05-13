package org.learne.platform.learne.domain.services.Exam;

import org.learne.platform.learne.domain.model.commands.Exam.CreateExamCommand;

public interface ExamCommandService {

    Long handle(CreateExamCommand command);
}
