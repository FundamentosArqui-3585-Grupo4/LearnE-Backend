package org.learne.platform.learne.domain.services.Unit;

import org.learne.platform.learne.domain.model.commands.Unit.CreateUnitCommand;

public interface UnitCommandService {
    Long handle(CreateUnitCommand command);
}
