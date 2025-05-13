package org.learne.platform.learne.application.internal.queryservices;

import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.learne.platform.learne.domain.model.queries.CoursesEnrollment.GetAllCoursesEnrollmentQuery;
import org.learne.platform.learne.domain.services.CoursesEnrollment.CoursesEnrollmentQueryService;
import org.learne.platform.learne.infrastructure.persistence.jpa.CoursesEnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesEnrollmentQueryServiceImpl implements CoursesEnrollmentQueryService {
    private final CoursesEnrollmentRepository coursesEnrollmentRepository;
    public CoursesEnrollmentQueryServiceImpl(CoursesEnrollmentRepository coursesEnrollmentRepository) {
        this.coursesEnrollmentRepository = coursesEnrollmentRepository;
    }
    @Override
    public List<CoursesEnrollment> handle(GetAllCoursesEnrollmentQuery query) {
        return coursesEnrollmentRepository.findAll();
    }
}
