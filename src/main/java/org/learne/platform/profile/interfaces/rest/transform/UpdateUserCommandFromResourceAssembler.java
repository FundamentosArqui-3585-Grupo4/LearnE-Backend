package org.learne.platform.profile.interfaces.rest.transform;

import org.learne.platform.profile.domain.model.commands.UpdateUserCommand;
import org.learne.platform.profile.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    public static UpdateUserCommand ToCommandFromResource(Long userId, UpdateUserResource resource) {
        return new UpdateUserCommand(userId, resource.firstName(), resource.lastName(), resource.username(),
                resource.email(), resource.password(), resource.type_user(), resource.type_plan());
    }
}
