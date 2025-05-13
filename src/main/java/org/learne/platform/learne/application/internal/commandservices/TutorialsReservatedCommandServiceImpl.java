package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.TutorialsReservated;
import org.learne.platform.learne.domain.model.commands.CreateTutorialsReservatedCommand;
import org.learne.platform.learne.domain.services.TutorialsReservated.TutorialsReservatedCommandService;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsReservatedRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TutorialsReservatedCommandServiceImpl implements TutorialsReservatedCommandService {
    private final TutorialsReservatedRepository tutorialsReservatedRepository;
    public TutorialsReservatedCommandServiceImpl(TutorialsReservatedRepository tutorialsReservatedRepository) {
        this.tutorialsReservatedRepository = tutorialsReservatedRepository;
    }

    @Override
    public Optional<TutorialsReservated> handle(CreateTutorialsReservatedCommand command) {
        if (tutorialsReservatedRepository.existsByUserIdAndTutorialsCoursesId(command.studentId(), command.tutorialId())) {
            throw new IllegalArgumentException("Student Id and Tutorial Id already exists");
        }
        var tutorialsReservated = new TutorialsReservated(command);
        var createTutorialsReservated = tutorialsReservatedRepository.save(tutorialsReservated);
        return Optional.of(createTutorialsReservated);
    }
}
