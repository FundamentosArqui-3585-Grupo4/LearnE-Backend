package org.learne.platform.learne.domain.model.commands.Exam;

public record CreateExamCommand(Long unitId, Long courseId,
                                String title) {

    public CreateExamCommand {
        if(unitId == null) {
            throw new NullPointerException("Unit id is required");
        }
        if(unitId <= 0) {
            throw new IllegalArgumentException("Unit id must be a positive number");
        }
        if(courseId == null) {
            throw new NullPointerException("Course id is required");
        }
        if(courseId <= 0) {
            throw new IllegalArgumentException("Course id must be a positive number");
        }
        if(title == null || title.isBlank()) {
            throw new NullPointerException("Title is required");
        }
    }
}
