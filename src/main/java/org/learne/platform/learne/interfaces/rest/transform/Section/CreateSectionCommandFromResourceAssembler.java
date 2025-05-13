package org.learne.platform.learne.interfaces.rest.transform.Section;

import org.learne.platform.learne.domain.model.commands.Section.CreateSectionCommand;
import org.learne.platform.learne.interfaces.rest.resources.Section.CreateSectionResource;

public class CreateSectionCommandFromResourceAssembler {
    public static CreateSectionCommand ToCommandFromResource(CreateSectionResource resource) {
        return new CreateSectionCommand(resource.unit_id(), resource.title(), resource.description(),
                resource.url_video());
    }
}
