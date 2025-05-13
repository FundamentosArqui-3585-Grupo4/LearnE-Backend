package org.learne.platform.learne.domain.model.queries.Question;

public record GetQuestionByIdQuery(Long id) {

    public GetQuestionByIdQuery {
        if(id == null) {
            throw new NullPointerException("Id is required");
        }
        if(id <= 0) {
            throw new IllegalArgumentException("Id must be a positive number");
        }
    }
}
