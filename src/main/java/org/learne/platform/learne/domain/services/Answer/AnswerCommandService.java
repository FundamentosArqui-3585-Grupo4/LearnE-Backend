package org.learne.platform.learne.domain.services.Answer;

import org.learne.platform.learne.domain.model.commands.Answer.CreateAnswerCommand;

public interface AnswerCommandService {

    Long handle(CreateAnswerCommand command);

}
