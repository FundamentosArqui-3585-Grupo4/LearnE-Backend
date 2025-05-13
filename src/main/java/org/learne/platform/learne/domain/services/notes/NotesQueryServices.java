package org.learne.platform.learne.domain.services.notes;

import org.learne.platform.learne.domain.model.aggregates.Notes;
import org.learne.platform.learne.domain.model.queries.Notes.GetAllNotesQuery;
import org.learne.platform.learne.domain.model.queries.Notes.GetNotesByStudentIdQuery;

import java.util.List;
import java.util.Optional;

public interface NotesQueryServices {
    Optional<Notes> handle(GetNotesByStudentIdQuery query);
    List<Notes> handle(GetAllNotesQuery query);
}
