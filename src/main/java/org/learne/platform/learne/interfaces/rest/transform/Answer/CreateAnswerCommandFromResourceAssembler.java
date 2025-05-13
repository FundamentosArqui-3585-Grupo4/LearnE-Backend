package org.learne.platform.learne.interfaces.rest.transform.Answer;

import org.learne.platform.learne.domain.model.commands.Answer.CreateAnswerCommand;
import org.learne.platform.learne.interfaces.rest.resources.Answer.CreateAnswerResource;

public class CreateAnswerCommandFromResourceAssembler {

    public static CreateAnswerCommand ToCommandFromResource(CreateAnswerResource resource) {
        return new CreateAnswerCommand(resource.question_id(), resource.is_correct(), resource.description());
    }
}
