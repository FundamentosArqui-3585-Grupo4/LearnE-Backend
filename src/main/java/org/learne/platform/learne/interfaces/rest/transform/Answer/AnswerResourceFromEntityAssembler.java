package org.learne.platform.learne.interfaces.rest.transform.Answer;

import org.learne.platform.learne.domain.model.aggregates.Answer;
import org.learne.platform.learne.interfaces.rest.resources.Answer.AnswerResource;

public class AnswerResourceFromEntityAssembler {

    public static AnswerResource ToResourceFromEntity(Answer entity) {
        return new AnswerResource(entity.getId(), entity.getQuestion().getId(),
                entity.isCorrect(), entity.getDescription());
    }
}
