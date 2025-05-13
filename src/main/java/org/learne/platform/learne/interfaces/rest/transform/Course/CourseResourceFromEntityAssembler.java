package org.learne.platform.learne.interfaces.rest.transform.Course;

import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.interfaces.rest.resources.Course.CourseResource;

public class CourseResourceFromEntityAssembler {
    public static CourseResource toResourceFromEntity(Course entity) {
        return new CourseResource(entity.getId(), entity.getTitle(), entity.getDescription(),
                entity.getUser().getId(), entity.getLevel(), entity.getDuration(), entity.getPrior_knowledge(),
                entity.getPrincipal_image(), entity.getUrl_video());
    }
}
