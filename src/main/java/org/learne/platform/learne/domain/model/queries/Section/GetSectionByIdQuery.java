package org.learne.platform.learne.domain.model.queries.Section;

public record GetSectionByIdQuery(Long id) {
    public GetSectionByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }
}
