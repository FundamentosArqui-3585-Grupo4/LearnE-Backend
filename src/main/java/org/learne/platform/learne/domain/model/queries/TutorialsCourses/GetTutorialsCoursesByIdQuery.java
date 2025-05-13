package org.learne.platform.learne.domain.model.queries.TutorialsCourses;

public record GetTutorialsCoursesByIdQuery(Long id) {
    public GetTutorialsCoursesByIdQuery {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be a positive number");
        }
    }
}
