package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.domain.model.commands.CreatedCourseCommand;
import org.learne.platform.learne.domain.services.Course.CourseCommandService;
import org.learne.platform.learne.infrastructure.persistence.jpa.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseCommandServiceImpl implements CourseCommandService {

    private final CourseRepository courseRepository;

    public CourseCommandServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Optional<Course> handle(CreatedCourseCommand command) {
        if (courseRepository.existsByTitle(command.title()))
            throw new IllegalArgumentException("Title already exists.");
        var course = new Course(command);
        var createCourse = courseRepository.save(course);
        return Optional.of(createCourse);
    }
}
