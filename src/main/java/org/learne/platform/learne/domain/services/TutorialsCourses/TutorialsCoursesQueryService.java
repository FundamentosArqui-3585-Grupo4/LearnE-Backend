package org.learne.platform.learne.domain.services.TutorialsCourses;

import org.learne.platform.learne.domain.model.aggregates.TutorialsCourses;
import org.learne.platform.learne.domain.model.queries.TutorialsCourses.GetAllTutorialsCoursesQuery;
import org.learne.platform.learne.domain.model.queries.TutorialsCourses.GetTutorialsCoursesByIdQuery;

import java.util.List;
import java.util.Optional;

public interface TutorialsCoursesQueryService {
    Optional<TutorialsCourses> handle(GetTutorialsCoursesByIdQuery query);
    List<TutorialsCourses> handle(GetAllTutorialsCoursesQuery query);
}
