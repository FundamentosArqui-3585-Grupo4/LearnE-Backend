package org.learne.platform.learne.domain.model.commands.Note;

public record CreateNotesCommand(Long studentId, Long examId, Float note) {
    public CreateNotesCommand{
        if (studentId == null ){
            throw new NullPointerException("Student ID is required");
        }
        if(studentId <= 0){
            throw new IllegalArgumentException("Student ID must be a positive number");
        }
        if(examId==null ){
            throw new NullPointerException("Exam ID is required");
        }

        if(examId<=0){
            throw new IllegalArgumentException("Exam ID must be a positive number");
        }

        if(note==null ){
            throw new NullPointerException("Note is required");
        }
        if(note<=0||note>20){
            throw new IllegalArgumentException("The note is not within the range");
        }
    }

}





