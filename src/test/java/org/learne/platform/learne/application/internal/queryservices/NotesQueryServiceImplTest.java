package org.learne.platform.learne.application.internal.queryservices;

import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Notes;
import org.learne.platform.learne.domain.model.queries.Notes.GetAllNotesQuery;
import org.learne.platform.learne.domain.model.queries.Notes.GetNotesByStudentIdQuery;
import org.learne.platform.learne.infrastructure.persistence.jpa.NotesRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotesQueryServiceImplTest {

    private final NotesRepository notesRepository = mock(NotesRepository.class);
    private final NotesQueryServiceImpl notesQueryService = new NotesQueryServiceImpl(notesRepository);

    @Test
    void handle_shouldReturnNoteByStudentId() {
        Long studentId = 1L;
        Notes note = mock(Notes.class);

        when(notesRepository.findById(studentId)).thenReturn(Optional.of(note));

        var result = notesQueryService.handle(new GetNotesByStudentIdQuery(studentId));

        assertTrue(result.isPresent());
        assertEquals(note, result.get());
    }

    @Test
    void handle_shouldReturnAllNotes() {
        Notes note1 = mock(Notes.class);
        Notes note2 = mock(Notes.class);

        when(notesRepository.findAll()).thenReturn(List.of(note1, note2));

        var result = notesQueryService.handle(new GetAllNotesQuery());

        assertEquals(2, result.size());
    }
}