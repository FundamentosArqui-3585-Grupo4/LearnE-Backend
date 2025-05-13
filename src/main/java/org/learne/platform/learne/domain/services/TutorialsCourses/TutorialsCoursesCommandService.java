package org.learne.platform.learne.domain.services.TutorialsCourses;

import org.learne.platform.learne.domain.model.aggregates.TutorialsCourses;
import org.learne.platform.learne.domain.model.commands.TutorialsCourses.CreateTutorialsCoursesCommand;
import org.learne.platform.learne.domain.model.commands.TutorialsCourses.UpdateTutorialsCoursesCommand;

import java.util.Optional;

public interface TutorialsCoursesCommandService {
    Optional<TutorialsCourses> handle(UpdateTutorialsCoursesCommand command);
    Long handle(CreateTutorialsCoursesCommand command);
}
