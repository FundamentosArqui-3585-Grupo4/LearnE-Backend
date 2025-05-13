package org.learne.platform.learne.interfaces.rest.transform.Notes;

import org.learne.platform.learne.domain.model.aggregates.Notes;
import org.learne.platform.learne.interfaces.rest.resources.Notes.NoteResource;

public class NotesResourceFromEntityAssembler {
    public static NoteResource ToResourceFromEntity(Notes entity) {
        return new NoteResource(entity.getId(),entity.getStudentId().getId(),
                entity.getExamId().getId(),entity.getNote());
    }
}
