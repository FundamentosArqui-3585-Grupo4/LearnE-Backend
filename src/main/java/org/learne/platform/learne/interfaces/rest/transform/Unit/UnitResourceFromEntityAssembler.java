package org.learne.platform.learne.interfaces.rest.transform.Unit;

import org.learne.platform.learne.domain.model.aggregates.Unit;
import org.learne.platform.learne.interfaces.rest.resources.Unit.UnitResource;

public class UnitResourceFromEntityAssembler {
    public static UnitResource ToResourceFromEntity(Unit entity) {
        return new UnitResource(entity.getId(), entity.getCourses().getId(), entity.getTitle());
    }
}
