package org.learne.platform.learne.domain.model.queries;

public record GetCourseByIdQuery(Long id) {
    public GetCourseByIdQuery {
        if(id == null || id <= 0){
            throw new IllegalArgumentException("Id must required.");
        }
    }
}
