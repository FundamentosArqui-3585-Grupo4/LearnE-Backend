package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.Notes;
import org.learne.platform.learne.domain.model.commands.Note.CreateNotesCommand;
import org.learne.platform.learne.domain.services.notes.NotesCommandService;
import org.learne.platform.learne.infrastructure.persistence.jpa.NotesRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteCommandServiceImpl implements NotesCommandService {

    private final NotesRepository notesRepository;

    public NoteCommandServiceImpl(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public Long handle(CreateNotesCommand command) {

        var newNote = new Notes(command);
        try {
            notesRepository.save(newNote);
        }catch (RuntimeException e) {
            throw new IllegalArgumentException("An error occurred while saving note" + e.getMessage());
        }
        return newNote.getId();
    }
}
