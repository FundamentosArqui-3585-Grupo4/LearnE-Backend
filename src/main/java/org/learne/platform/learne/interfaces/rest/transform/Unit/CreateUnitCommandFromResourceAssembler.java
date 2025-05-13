package org.learne.platform.learne.interfaces.rest.transform.Unit;

import org.learne.platform.learne.domain.model.commands.Unit.CreateUnitCommand;
import org.learne.platform.learne.interfaces.rest.resources.Unit.CreateUnitResource;

public class CreateUnitCommandFromResourceAssembler {
    public static CreateUnitCommand ToCommandFromResource(CreateUnitResource resource) {
        return new CreateUnitCommand(resource.course_id(), resource.title());
    }
}
