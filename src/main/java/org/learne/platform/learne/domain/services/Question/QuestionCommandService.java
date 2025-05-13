package org.learne.platform.learne.domain.services.Question;

import org.learne.platform.learne.domain.model.commands.Question.CreateQuestionCommand;

public interface QuestionCommandService {

    Long handle(CreateQuestionCommand command);
}
