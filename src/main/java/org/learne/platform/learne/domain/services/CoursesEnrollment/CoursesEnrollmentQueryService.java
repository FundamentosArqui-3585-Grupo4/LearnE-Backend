package org.learne.platform.learne.domain.services.CoursesEnrollment;

import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.learne.platform.learne.domain.model.queries.CoursesEnrollment.GetAllCoursesEnrollmentQuery;

import java.util.List;

public interface CoursesEnrollmentQueryService {
    List<CoursesEnrollment> handle(GetAllCoursesEnrollmentQuery query);
}
