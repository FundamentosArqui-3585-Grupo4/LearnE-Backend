package org.learne.platform.learne.interfaces.rest.transform.Exam;

import org.learne.platform.learne.domain.model.aggregates.Exam;
import org.learne.platform.learne.interfaces.rest.resources.Exam.ExamResource;

public class ExamResourceFromEntityAssembler {

    public static ExamResource ToResourceFromEntity(Exam entity) {
        return new ExamResource(entity.getId(), entity.getUnit().getId(),
                entity.getCourse().getId(), entity.getTitle());
    }
}
