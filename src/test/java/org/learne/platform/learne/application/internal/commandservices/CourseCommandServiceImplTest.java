package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.domain.model.commands.CreatedCourseCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.CourseRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseCommandServiceImplTest {

    private CourseRepository courseRepository;
    private CourseCommandServiceImpl courseCommandService;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        courseCommandService = new CourseCommandServiceImpl(courseRepository);
    }

    @Test
    void shouldCreateCourseWhenTitleNotExists() {
        // Arrange
        CreatedCourseCommand command = new CreatedCourseCommand(
                "Java Básico", "Curso de introducción", 1L,
                "Beginner", "4 semanas", "Ninguno", "img.png", "video.com");

        Course mockCourse = new Course(command);

        when(courseRepository.existsByTitle("Java Básico")).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenReturn(mockCourse);

        // Act
        Optional<Course> result = courseCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Java Básico", result.get().getTitle());
        verify(courseRepository, times(1)).existsByTitle("Java Básico");
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldThrowExceptionWhenTitleAlreadyExists() {
        // Arrange
        CreatedCourseCommand command = new CreatedCourseCommand(
                "Java Básico", "Curso de introducción", 1L,
                "Beginner", "4 semanas", "Ninguno", "img.png", "video.com");

        when(courseRepository.existsByTitle("Java Básico")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> courseCommandService.handle(command));
        verify(courseRepository, times(1)).existsByTitle("Java Básico");
        verify(courseRepository, never()).save(any(Course.class));
    }
}