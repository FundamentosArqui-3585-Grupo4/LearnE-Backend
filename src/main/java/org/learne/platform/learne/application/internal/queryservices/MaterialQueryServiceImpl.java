package org.learne.platform.learne.application.internal.queryservices;

import org.learne.platform.learne.domain.model.aggregates.Material;
import org.learne.platform.learne.domain.model.queries.Material.GetAllMaterialQuery;
import org.learne.platform.learne.domain.model.queries.Material.GetMaterialByIdQuery;
import org.learne.platform.learne.domain.services.Material.MaterialQueryService;
import org.learne.platform.learne.infrastructure.persistence.jpa.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MaterialQueryServiceImpl implements MaterialQueryService {
    private final MaterialRepository materialRepository;

    public MaterialQueryServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }


    @Override
    public Optional<Material> handle(GetMaterialByIdQuery query) {
        return materialRepository.findById(query.id());
    }

    @Override
    public List<Material> handle(GetAllMaterialQuery query) {
        return materialRepository.findAll();
    }
}
