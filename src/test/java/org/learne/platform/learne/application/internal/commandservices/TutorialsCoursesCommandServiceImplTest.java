package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.TutorialsCourses;
import org.learne.platform.learne.domain.model.commands.TutorialsCourses.CreateTutorialsCoursesCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsCoursesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TutorialsCoursesCommandServiceImplTest {

    private TutorialsCoursesRepository repository;
    private TutorialsCoursesCommandServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(TutorialsCoursesRepository.class);
        service = new TutorialsCoursesCommandServiceImpl(repository);
    }

    @Test
    void handle_shouldCreateTutorialSuccessfully() {
        // Arrange
        CreateTutorialsCoursesCommand command = new CreateTutorialsCoursesCommand(
                1L, 2L, "2025-12-01", "10:00", false, "https://link"
        );

        when(repository.existsByCourseIdAndDateAndHour(1L, "2025-12-01", "10:00")).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> {
            TutorialsCourses saved = invocation.getArgument(0);
            saved.setId(999L);
            return saved;
        });

        // Act
        Long result = service.handle(command);

        // Assert
        assertNotNull(result);
        assertEquals(999L, result);
        verify(repository).save(any());
    }

    @Test
    void handle_shouldThrowExceptionIfTutorialAlreadyExists() {
        // Arrange
        CreateTutorialsCoursesCommand command = new CreateTutorialsCoursesCommand(
                1L, 2L, "2025-12-01", "10:00", false, "https://link"
        );

        when(repository.existsByCourseIdAndDateAndHour(1L, "2025-12-01", "10:00")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        verify(repository, never()).save(any());
    }
}