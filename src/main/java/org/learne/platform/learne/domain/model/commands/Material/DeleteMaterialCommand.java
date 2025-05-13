package org.learne.platform.learne.domain.model.commands.Material;

public record DeleteMaterialCommand(Long id) {
    public DeleteMaterialCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null or less than zero");
        }
    }
}
