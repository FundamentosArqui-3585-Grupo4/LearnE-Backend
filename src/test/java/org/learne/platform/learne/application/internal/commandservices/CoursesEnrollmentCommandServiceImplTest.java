package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.learne.platform.learne.domain.model.commands.CoursesEnrollment.CreateCoursesEnrollmentCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.CoursesEnrollmentRepository;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoursesEnrollmentCommandServiceImplTest {

    private CoursesEnrollmentRepository coursesEnrollmentRepository;
    private CoursesEnrollmentCommandServiceImpl commandService;

    @BeforeEach
    void setUp() {
        coursesEnrollmentRepository = mock(CoursesEnrollmentRepository.class);
        commandService = new CoursesEnrollmentCommandServiceImpl(coursesEnrollmentRepository);
    }

    @Test
    void createEnrollment_shouldReturnEnrollment() throws Exception {
        CreateCoursesEnrollmentCommand command = new CreateCoursesEnrollmentCommand(1L, 2L);
        CoursesEnrollment enrollment = new CoursesEnrollment(command);
        setPrivateId(enrollment, 77L);

        when(coursesEnrollmentRepository.existsByUserIdAndCourseId(1L, 2L)).thenReturn(false);
        when(coursesEnrollmentRepository.save(any(CoursesEnrollment.class))).thenReturn(enrollment);

        Optional<CoursesEnrollment> result = commandService.handle(command);

        assertTrue(result.isPresent());
        assertEquals(77L, result.get().getId());
        verify(coursesEnrollmentRepository).save(any(CoursesEnrollment.class));
    }

    @Test
    void createEnrollment_shouldThrowIfExists() {
        CreateCoursesEnrollmentCommand command = new CreateCoursesEnrollmentCommand(1L, 2L);
        when(coursesEnrollmentRepository.existsByUserIdAndCourseId(1L, 2L)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> commandService.handle(command));
    }

    private void setPrivateId(CoursesEnrollment enrollment, Long id) throws Exception {
        Field idField = enrollment.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(enrollment, id);
    }
}