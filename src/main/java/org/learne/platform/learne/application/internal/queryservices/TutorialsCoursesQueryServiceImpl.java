package org.learne.platform.learne.application.internal.queryservices;

import org.learne.platform.learne.domain.model.aggregates.TutorialsCourses;
import org.learne.platform.learne.domain.model.queries.TutorialsCourses.GetAllTutorialsCoursesQuery;
import org.learne.platform.learne.domain.model.queries.TutorialsCourses.GetTutorialsCoursesByIdQuery;
import org.learne.platform.learne.domain.services.TutorialsCourses.TutorialsCoursesQueryService;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsCoursesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialsCoursesQueryServiceImpl implements TutorialsCoursesQueryService {
    private final TutorialsCoursesRepository tutorialsCoursesRepository;
    public TutorialsCoursesQueryServiceImpl(TutorialsCoursesRepository tutorialsCoursesRepository) {
        this.tutorialsCoursesRepository = tutorialsCoursesRepository;
    }

    @Override
    public Optional<TutorialsCourses> handle(GetTutorialsCoursesByIdQuery query) {
        return tutorialsCoursesRepository.findById(query.id());
    }

    @Override
    public List<TutorialsCourses> handle(GetAllTutorialsCoursesQuery query) {
        return tutorialsCoursesRepository.findAll();
    }
}
