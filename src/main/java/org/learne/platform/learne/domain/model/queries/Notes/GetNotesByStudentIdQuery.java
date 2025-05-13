package org.learne.platform.learne.domain.model.queries.Notes;

public record GetNotesByStudentIdQuery(Long studentId){
    public GetNotesByStudentIdQuery {
        if(studentId == null){
            throw new NullPointerException("studentId is required");
        }
        if(studentId <= 0){
            throw new IllegalArgumentException("studentId must be positive");
        }
    }
}
