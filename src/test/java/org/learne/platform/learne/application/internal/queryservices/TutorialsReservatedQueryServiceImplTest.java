package org.learne.platform.learne.application.internal.queryservices;

import org.learne.platform.learne.domain.model.aggregates.TutorialsReservated;
import org.learne.platform.learne.domain.model.queries.TutorialsReservated.GetAllTutorialsReservatedQuery;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsReservatedRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TutorialsReservatedQueryServiceImplTest {

    private final TutorialsReservatedRepository repository = mock(TutorialsReservatedRepository.class);
    private final TutorialsReservatedQueryServiceImpl service = new TutorialsReservatedQueryServiceImpl(repository);

    @Test
    void handle_shouldReturnAllTutorialsReservated() {
        when(repository.findAll()).thenReturn(List.of(new TutorialsReservated(), new TutorialsReservated()));

        var result = service.handle(new GetAllTutorialsReservatedQuery());

        assertEquals(2, result.size());
    }
}