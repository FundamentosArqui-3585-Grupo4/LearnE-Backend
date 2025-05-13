package org.learne.platform.learne.domain.services.CoursesEnrollment;

import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.learne.platform.learne.domain.model.commands.CoursesEnrollment.CreateCoursesEnrollmentCommand;

import java.util.Optional;

public interface CoursesEnrollmentCommandService {
    Optional<CoursesEnrollment> handle(CreateCoursesEnrollmentCommand command);
}
