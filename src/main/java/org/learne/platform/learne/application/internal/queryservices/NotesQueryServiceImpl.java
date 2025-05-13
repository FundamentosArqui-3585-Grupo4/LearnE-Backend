package org.learne.platform.learne.application.internal.queryservices;

import org.learne.platform.learne.domain.model.aggregates.Notes;
import org.learne.platform.learne.domain.model.queries.Notes.GetAllNotesQuery;
import org.learne.platform.learne.domain.model.queries.Notes.GetNotesByStudentIdQuery;
import org.learne.platform.learne.domain.services.notes.NotesQueryServices;
import org.learne.platform.learne.infrastructure.persistence.jpa.NotesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class NotesQueryServiceImpl implements NotesQueryServices {

    private final NotesRepository notesRepository;

    public NotesQueryServiceImpl(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public Optional<Notes> handle(GetNotesByStudentIdQuery query) {
        return notesRepository.findById(query.studentId());
    }

    @Override
    public List<Notes> handle(GetAllNotesQuery query) {
        return notesRepository.findAll();
    }
}
