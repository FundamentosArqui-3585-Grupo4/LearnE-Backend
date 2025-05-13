package org.learne.platform.learne.domain.services.Course;

import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.domain.model.commands.CreatedCourseCommand;

import java.util.Optional;

public interface CourseCommandService {
    Optional<Course> handle(CreatedCourseCommand command);
}
