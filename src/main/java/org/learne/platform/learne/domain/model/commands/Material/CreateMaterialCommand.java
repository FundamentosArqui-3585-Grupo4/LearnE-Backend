package org.learne.platform.learne.domain.model.commands.Material;

import org.learne.platform.learne.domain.model.aggregates.Material;

public record CreateMaterialCommand(Long courseId, String title,
                                    String format, String link) {
    public CreateMaterialCommand {

        if(courseId == null) {
            throw new NullPointerException("Course id is required");
        }
        if(courseId <= 0) {
            throw new IllegalArgumentException("Course id must be a positive number");
        }
        if(title == null || title.isBlank()) {
            throw new NullPointerException("Title is required");
        }
        if(format == null || format.isBlank()) {
            throw new NullPointerException("Title is required");
        }
        if(link == null || link.isBlank()) {
            throw new NullPointerException("Title is required");
        }


    }
}
