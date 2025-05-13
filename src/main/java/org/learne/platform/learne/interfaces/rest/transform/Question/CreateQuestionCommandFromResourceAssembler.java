package org.learne.platform.learne.interfaces.rest.transform.Question;

import org.learne.platform.learne.domain.model.commands.Question.CreateQuestionCommand;
import org.learne.platform.learne.interfaces.rest.resources.Question.CreateQuestionResource;

public class CreateQuestionCommandFromResourceAssembler {

    public static CreateQuestionCommand ToCommandFromResource(CreateQuestionResource resource) {
        return new CreateQuestionCommand(resource.exam_id(), resource.question());
    }
}
