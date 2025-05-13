package org.learne.platform.learne.domain.services.Material;

import org.learne.platform.learne.domain.model.aggregates.Material;
import org.learne.platform.learne.domain.model.queries.Material.GetAllMaterialQuery;
import org.learne.platform.learne.domain.model.queries.Material.GetMaterialByIdQuery;

import java.util.List;
import java.util.Optional;

public interface MaterialQueryService {
    Optional<Material> handle(GetMaterialByIdQuery query);

    List<Material> handle(GetAllMaterialQuery query);
}
