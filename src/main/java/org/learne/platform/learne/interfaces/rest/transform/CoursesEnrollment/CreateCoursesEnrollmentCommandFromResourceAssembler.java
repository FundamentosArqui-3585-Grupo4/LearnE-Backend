package org.learne.platform.learne.interfaces.rest.transform.CoursesEnrollment;

import org.learne.platform.learne.domain.model.commands.CoursesEnrollment.CreateCoursesEnrollmentCommand;
import org.learne.platform.learne.interfaces.rest.resources.CoursesEnrollment.CreateCoursesEnrollmentResource;

public class CreateCoursesEnrollmentCommandFromResourceAssembler {
    public static CreateCoursesEnrollmentCommand toCommandFromResource(CreateCoursesEnrollmentResource resource) {
        return new CreateCoursesEnrollmentCommand(resource.student_id(), resource.course_id());
    }
}
