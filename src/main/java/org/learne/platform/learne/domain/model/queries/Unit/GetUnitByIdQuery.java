package org.learne.platform.learne.domain.model.queries.Unit;

public record GetUnitByIdQuery(Long id) {
    public GetUnitByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }
}
