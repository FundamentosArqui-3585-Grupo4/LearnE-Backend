package org.learne.platform.learne.interfaces.rest.resources.Notes;

import javax.swing.*;

public record CreateNotesResource(Long student_id, Long exam_id, Float note) {
}
