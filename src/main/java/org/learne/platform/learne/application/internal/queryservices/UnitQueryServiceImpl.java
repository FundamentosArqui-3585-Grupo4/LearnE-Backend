package org.learne.platform.learne.application.internal.queryservices;

import org.learne.platform.learne.domain.model.aggregates.Unit;
import org.learne.platform.learne.domain.model.queries.Unit.GetAllUnitsQuery;
import org.learne.platform.learne.domain.model.queries.Unit.GetUnitByIdQuery;
import org.learne.platform.learne.domain.services.Unit.UnitQueryService;
import org.learne.platform.learne.infrastructure.persistence.jpa.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitQueryServiceImpl implements UnitQueryService {
    private final UnitRepository unitRepository;
    public UnitQueryServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }
    @Override
    public List<Unit> handle(GetAllUnitsQuery query) {
        return unitRepository.findAll();
    }
    @Override
    public Optional<Unit> handle(GetUnitByIdQuery query) {
        return unitRepository.findById(query.id());
    }
}
