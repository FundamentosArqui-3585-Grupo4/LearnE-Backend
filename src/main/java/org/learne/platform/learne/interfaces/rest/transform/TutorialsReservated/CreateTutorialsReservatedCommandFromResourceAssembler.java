package org.learne.platform.learne.interfaces.rest.transform.TutorialsReservated;

import org.learne.platform.learne.domain.model.commands.CreateTutorialsReservatedCommand;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsReservated.CreateTutorialsReservatedResource;

public class CreateTutorialsReservatedCommandFromResourceAssembler {
    public static CreateTutorialsReservatedCommand toCommand(CreateTutorialsReservatedResource resource) {
        return new CreateTutorialsReservatedCommand(resource.student_id(), resource.tutorial_id());
    }
}
