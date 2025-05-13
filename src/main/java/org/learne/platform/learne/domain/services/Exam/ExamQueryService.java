package org.learne.platform.learne.domain.services.Exam;

import org.learne.platform.learne.domain.model.aggregates.Exam;
import org.learne.platform.learne.domain.model.queries.Exam.GetAllExamsQuery;
import org.learne.platform.learne.domain.model.queries.Exam.GetExamByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ExamQueryService {

    Optional<Exam> handle(GetExamByIdQuery query);

    List<Exam> handle(GetAllExamsQuery query);
}
