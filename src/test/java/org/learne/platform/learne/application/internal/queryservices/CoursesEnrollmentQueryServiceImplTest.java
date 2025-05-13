package org.learne.platform.learne.application.internal.queryservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.learne.platform.learne.domain.model.commands.CoursesEnrollment.CreateCoursesEnrollmentCommand;
import org.learne.platform.learne.domain.model.queries.CoursesEnrollment.GetAllCoursesEnrollmentQuery;
import org.learne.platform.learne.infrastructure.persistence.jpa.CoursesEnrollmentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoursesEnrollmentQueryServiceImplTest {

    private CoursesEnrollmentRepository coursesEnrollmentRepository;
    private CoursesEnrollmentQueryServiceImpl queryService;

    @BeforeEach
    void setUp() {
        coursesEnrollmentRepository = mock(CoursesEnrollmentRepository.class);
        queryService = new CoursesEnrollmentQueryServiceImpl(coursesEnrollmentRepository);
    }

    @Test
    void handleGetAllCoursesEnrollment_shouldReturnList() {
        List<CoursesEnrollment> mockList = List.of(
                new CoursesEnrollment(new CreateCoursesEnrollmentCommand(1L, 1L)),
                new CoursesEnrollment(new CreateCoursesEnrollmentCommand(2L, 1L))
        );

        when(coursesEnrollmentRepository.findAll()).thenReturn(mockList);

        List<CoursesEnrollment> result = queryService.handle(new GetAllCoursesEnrollmentQuery());

        assertEquals(2, result.size());
        verify(coursesEnrollmentRepository).findAll();
    }
}