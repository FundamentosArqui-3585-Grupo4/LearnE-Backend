package org.learne.platform.learne.interfaces.rest.transform.CoursesEnrollment;

import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.learne.platform.learne.interfaces.rest.resources.CoursesEnrollment.CoursesEnrollmentResource;

public class CoursesEnrollmentResourceFromEntityAssembler {
    public static CoursesEnrollmentResource toResourceFromEntity(CoursesEnrollment entity) {
        return new CoursesEnrollmentResource(entity.getId(), entity.getUser().getId(), entity.getCourse().getId());
    }
}
