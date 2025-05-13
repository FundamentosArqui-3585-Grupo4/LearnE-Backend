package org.learne.platform.learne.domain.services.TutorialsReservated;

import org.learne.platform.learne.domain.model.aggregates.TutorialsReservated;
import org.learne.platform.learne.domain.model.commands.CreateTutorialsReservatedCommand;

import java.util.Optional;

public interface TutorialsReservatedCommandService {
    Optional<TutorialsReservated> handle(CreateTutorialsReservatedCommand command);
}
