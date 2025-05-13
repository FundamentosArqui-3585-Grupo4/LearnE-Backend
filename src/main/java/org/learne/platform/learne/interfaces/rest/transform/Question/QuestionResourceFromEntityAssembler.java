package org.learne.platform.learne.interfaces.rest.transform.Question;

import org.learne.platform.learne.domain.model.aggregates.Question;
import org.learne.platform.learne.interfaces.rest.resources.Question.QuestionResource;

public class QuestionResourceFromEntityAssembler {

    public static QuestionResource ToResourceFromEntity(Question entity) {
        return new QuestionResource(entity.getId(), entity.getExam().getId(),
                entity.getQuestion());
    }
}
