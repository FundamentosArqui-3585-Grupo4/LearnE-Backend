package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.TutorialsReservated;
import org.learne.platform.learne.domain.model.commands.CreateTutorialsReservatedCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsReservatedRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TutorialsReservatedCommandServiceImplTest {

    private final TutorialsReservatedRepository repository = mock(TutorialsReservatedRepository.class);
    private final TutorialsReservatedCommandServiceImpl service = new TutorialsReservatedCommandServiceImpl(repository);

    @Test
    void handle_shouldCreateTutorialsReservatedSuccessfully() {
        CreateTutorialsReservatedCommand command = new CreateTutorialsReservatedCommand(1L, 1L);
        TutorialsReservated expected = new TutorialsReservated(command);
        expected.setId(1L);

        when(repository.existsByUserIdAndTutorialsCoursesId(1L, 1L)).thenReturn(false);
        when(repository.save(any())).thenReturn(expected);

        Optional<TutorialsReservated> result = service.handle(command);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void handle_shouldThrowExceptionWhenDuplicate() {
        CreateTutorialsReservatedCommand command = new CreateTutorialsReservatedCommand(1L, 1L);
        when(repository.existsByUserIdAndTutorialsCoursesId(1L, 1L)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.handle(command));
    }
}