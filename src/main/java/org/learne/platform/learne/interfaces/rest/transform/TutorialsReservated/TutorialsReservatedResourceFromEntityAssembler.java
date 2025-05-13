package org.learne.platform.learne.interfaces.rest.transform.TutorialsReservated;

import org.learne.platform.learne.domain.model.aggregates.TutorialsReservated;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsReservated.TutorialsReservatedResource;

public class TutorialsReservatedResourceFromEntityAssembler {
    public static TutorialsReservatedResource toResourceFromEntity(TutorialsReservated entity) {
        return new TutorialsReservatedResource(entity.getId(), entity.getUser().getId(), entity.getTutorialsCourses().getId());
    }
}
