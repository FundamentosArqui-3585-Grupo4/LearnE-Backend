package org.learne.platform.learne.interfaces.rest.transform.Notes;

import org.learne.platform.learne.domain.model.aggregates.Notes;
import org.learne.platform.learne.domain.model.commands.Note.CreateNotesCommand;
import org.learne.platform.learne.interfaces.rest.resources.Exam.CreateExamResource;
import org.learne.platform.learne.interfaces.rest.resources.Notes.CreateNotesResource;

public class CreateNotesCommandFromResourceAssembler {
    public static CreateNotesCommand toCommandFromResource(CreateNotesResource resource) {
        return  new CreateNotesCommand(resource.student_id(),resource.exam_id(),resource.note());
    }

}