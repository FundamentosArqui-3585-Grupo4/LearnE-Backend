package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.TutorialsCourses;
import org.learne.platform.learne.domain.model.commands.TutorialsCourses.CreateTutorialsCoursesCommand;
import org.learne.platform.learne.domain.model.commands.TutorialsCourses.UpdateTutorialsCoursesCommand;
import org.learne.platform.learne.domain.services.TutorialsCourses.TutorialsCoursesCommandService;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsCoursesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TutorialsCoursesCommandServiceImpl implements TutorialsCoursesCommandService {
    private final TutorialsCoursesRepository tutorialsCoursesRepository;
    public TutorialsCoursesCommandServiceImpl(TutorialsCoursesRepository tutorialsCoursesRepository) {
        this.tutorialsCoursesRepository = tutorialsCoursesRepository;
    }

    @Override
    public Long handle(CreateTutorialsCoursesCommand command) {
        if (tutorialsCoursesRepository.existsByCourseIdAndDateAndHour(command.courseId(), command.date(), command.hour())) {
            throw new IllegalArgumentException("Tutorial already scheduled");
        }

        var newTutorialsCourses = new TutorialsCourses(command);
        var saved = tutorialsCoursesRepository.save(newTutorialsCourses);
        return saved.getId();
    }

    @Override
    public Optional<TutorialsCourses> handle(UpdateTutorialsCoursesCommand command) {
        var result = tutorialsCoursesRepository.findById(command.tutorialId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Tutorials Courses with id " + command.tutorialId() + " not found");
        }
        var tutorialsCoursesToUpdate = result.get();
        try {
            var updateTutorialsCourse = tutorialsCoursesRepository.save(tutorialsCoursesToUpdate.updateTutorialsCourse(
                    command.courseId(), command.teacherId(), command.date(), command.hour(),
                    command.isReservated(), command.link()));
            return Optional.of(updateTutorialsCourse);
        } catch (Exception e) {
            throw new IllegalArgumentException("An error occurred while updating the tutorials course " + e.getMessage());
        }
    }

}
