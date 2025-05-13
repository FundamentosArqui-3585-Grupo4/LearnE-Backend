package org.learne.platform.learne.domain.model.queries.Material;

public record GetMaterialByIdQuery(Long id) {
    public GetMaterialByIdQuery {
        if (id == null) {

            throw new IllegalArgumentException("id is required");
        }
        if (id < 0) {
            throw new IllegalArgumentException("id must be a positive number");
        }
    }
}
