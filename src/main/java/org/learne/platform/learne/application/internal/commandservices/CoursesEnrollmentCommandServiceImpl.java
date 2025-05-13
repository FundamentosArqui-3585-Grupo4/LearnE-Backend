package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.learne.platform.learne.domain.model.commands.CoursesEnrollment.CreateCoursesEnrollmentCommand;
import org.learne.platform.learne.domain.services.CoursesEnrollment.CoursesEnrollmentCommandService;
import org.learne.platform.learne.infrastructure.persistence.jpa.CoursesEnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoursesEnrollmentCommandServiceImpl implements CoursesEnrollmentCommandService {
    private final CoursesEnrollmentRepository coursesEnrollmentRepository;
    public CoursesEnrollmentCommandServiceImpl(CoursesEnrollmentRepository coursesEnrollmentRepository) {
        this.coursesEnrollmentRepository = coursesEnrollmentRepository;
    }
    @Override
    public Optional<CoursesEnrollment> handle(CreateCoursesEnrollmentCommand command) {
        if (coursesEnrollmentRepository.existsByUserIdAndCourseId(command.studentId(), command.courseId())) {
            throw new IllegalArgumentException("Student Id and Course Id is already exists");
        }
        var coursesEnrollment = new CoursesEnrollment(command);
        var createCoursesEnrollment = coursesEnrollmentRepository.save(coursesEnrollment);
        return Optional.of(createCoursesEnrollment);
    }
}
