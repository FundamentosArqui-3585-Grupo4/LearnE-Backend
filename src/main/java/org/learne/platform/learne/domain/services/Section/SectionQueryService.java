package org.learne.platform.learne.domain.services.Section;

import org.learne.platform.learne.domain.model.aggregates.Section;
import org.learne.platform.learne.domain.model.queries.Section.GetAllSectionsQuery;
import org.learne.platform.learne.domain.model.queries.Section.GetSectionByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SectionQueryService {
    Optional<Section> handle(GetSectionByIdQuery query);
    List<Section> handle(GetAllSectionsQuery query);
}
