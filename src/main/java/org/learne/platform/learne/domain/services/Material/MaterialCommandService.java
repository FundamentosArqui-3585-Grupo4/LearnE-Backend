package org.learne.platform.learne.domain.services.Material;

import org.learne.platform.learne.domain.model.commands.Material.CreateMaterialCommand;
import org.learne.platform.learne.domain.model.commands.Material.DeleteMaterialCommand;


public interface MaterialCommandService {
    void handle(DeleteMaterialCommand command);
    Long handle(CreateMaterialCommand command);
}
