package org.learne.platform.learne.interfaces.rest.transform.Section;

import org.learne.platform.learne.domain.model.aggregates.Section;
import org.learne.platform.learne.interfaces.rest.resources.Section.SectionResource;

public class SectionResourceFromEntityAssembler {
    public static SectionResource ToResourceFromEntity(Section entity) {
        return new SectionResource(entity.getId(), entity.getUnit().getId(), entity.getTitle(),
                entity.getDescription(), entity.getUrl_video());
    }
}
