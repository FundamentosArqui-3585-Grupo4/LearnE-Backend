package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.TutorialsCourses;
import org.learne.platform.learne.domain.model.commands.TutorialsCourses.CreateTutorialsCoursesCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsCoursesRepository;

import java.util.Optional;

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
        CreateTutorialsCoursesCommand command = new CreateTutorialsCoursesCommand(
                1L, 2L, "2025-12-01", "10:00", false, "https://link"
        );

        TutorialsCourses tutorial = new TutorialsCourses(command);
        tutorial.setId(999L); // Estableces el ID manualmente

        when(repository.existsByCourseIdAndDateAndHour(1L, "2025-12-01", "10:00")).thenReturn(false);
        when(repository.save(any())).thenReturn(tutorial); // Devuelve el tutorial con ID seteado

        Long result = service.handle(command);

        assertNotNull(result);
        assertEquals(999L, result);
    }

    @Test
    void handle_shouldUpdateTutorialSuccessfully() {
        TutorialsCourses tutorial = new TutorialsCourses(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(tutorial));
        when(repository.save(any())).thenReturn(tutorial);

        var result = service.handle(new org.learne.platform.learne.domain.model.commands.TutorialsCourses.UpdateTutorialsCoursesCommand(
                1L, 2L, 2L, "2025-12-01", "10:00", true, "https://updated"
        ));

        assertTrue(result.isPresent());
    }
}