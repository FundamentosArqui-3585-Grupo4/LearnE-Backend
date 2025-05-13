package org.learne.platform.learne.interfaces.rest.transform.TutorialsCourses;

import org.learne.platform.learne.domain.model.aggregates.TutorialsCourses;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses.TutorialsCoursesResource;

public class TutorialsCoursesResourceFromEntityAssembler {
    public static TutorialsCoursesResource toResourceFromEntity(TutorialsCourses entity) {
        return new TutorialsCoursesResource(entity.getId(), entity.getCourse().getId(),
                entity.getUser().getId(), entity.getDate(), entity.getHour(), entity.getIsReservated(),
                entity.getLink());
    }
}
