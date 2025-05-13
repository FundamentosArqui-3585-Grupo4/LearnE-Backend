package org.learne.platform.learne.domain.services.notes;

import org.learne.platform.learne.domain.model.commands.Note.CreateNotesCommand;


public interface NotesCommandService {

    Long handle(CreateNotesCommand command);
}
