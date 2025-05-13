package org.learne.platform.learne.interfaces.rest.transform.Material;

import org.learne.platform.learne.domain.model.aggregates.Material;
import org.learne.platform.learne.domain.model.commands.Material.CreateMaterialCommand;
import org.learne.platform.learne.interfaces.rest.resources.Material.CreateMaterialResource;

public class CreateMaterialCommandFromResourceAssembler {

    public static CreateMaterialCommand ToCommandFromResource(CreateMaterialResource resource) {
        return new CreateMaterialCommand(resource.course_id(),resource.title(),resource.format(),resource.link());
    }
}
