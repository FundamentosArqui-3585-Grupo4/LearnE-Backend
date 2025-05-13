package org.learne.platform.learne.domain.model.commands.Unit;

public record CreateUnitCommand(Long courseId, String title) {
    public CreateUnitCommand {
        if(courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course Id can't be null or less than 0");
        }
        if(title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title can't be null or empty");
        }
    }
}
