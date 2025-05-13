package org.learne.platform.learne.interfaces.rest.transform.Material;

import org.learne.platform.learne.domain.model.aggregates.Material;
import org.learne.platform.learne.interfaces.rest.resources.Material.MaterialResource;

public class MaterialResourceFromEntityAssembler {
    public static MaterialResource ToResourceFromEntity(Material entity) {
        return new MaterialResource(entity.getId(),entity.getCourse().getId(),
                entity.getTitle(),entity.getFormat(),entity.getLink());

    }
}
