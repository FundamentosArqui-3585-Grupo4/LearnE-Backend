package org.learne.platform.learne.domain.services.Unit;

import org.learne.platform.learne.domain.model.aggregates.Unit;
import org.learne.platform.learne.domain.model.queries.Unit.GetAllUnitsQuery;
import org.learne.platform.learne.domain.model.queries.Unit.GetUnitByIdQuery;

import java.util.List;
import java.util.Optional;

public interface UnitQueryService {
    List<Unit> handle(GetAllUnitsQuery query);
    Optional<Unit> handle(GetUnitByIdQuery query);
}
