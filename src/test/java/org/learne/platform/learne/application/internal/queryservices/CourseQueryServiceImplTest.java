package org.learne.platform.learne.application.internal.queryservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.domain.model.commands.CreatedCourseCommand;
import org.learne.platform.learne.domain.model.queries.GetAllCoursesQuery;
import org.learne.platform.learne.domain.model.queries.GetCourseByIdQuery;
import org.learne.platform.learne.infrastructure.persistence.jpa.CourseRepository;
import org.learne.platform.profile.domain.model.aggregates.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseQueryServiceImplTest {

    private CourseRepository courseRepository;
    private CourseQueryServiceImpl courseQueryService;

    private Course sampleCourse;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        courseQueryService = new CourseQueryServiceImpl(courseRepository);

        // Create dummy data
        CreatedCourseCommand command = new CreatedCourseCommand(
                "Sample Title",
                "Sample Description",
                1L,
                "Beginner",
                "3h",
                "Basic knowledge",
                "image.jpg",
                "video.mp4"
        );

        sampleCourse = new Course(command);
        sampleCourse.setUser(new User(1L)); // Set teacher
    }

    @Test
    void handleGetAllCoursesQuery_shouldReturnCourseList() {
        when(courseRepository.findAll()).thenReturn(List.of(sampleCourse));

        List<Course> result = courseQueryService.handle(new GetAllCoursesQuery());

        assertEquals(1, result.size());
        assertEquals("Sample Title", result.get(0).getTitle());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void handleGetCourseByIdQuery_shouldReturnCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(sampleCourse));

        Optional<Course> result = courseQueryService.handle(new GetCourseByIdQuery(1L));

        assertTrue(result.isPresent());
        assertEquals("Sample Title", result.get().getTitle());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void handleGetCourseByIdQuery_shouldReturnEmpty() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Course> result = courseQueryService.handle(new GetCourseByIdQuery(99L));

        assertFalse(result.isPresent());
        verify(courseRepository, times(1)).findById(99L);
    }
}