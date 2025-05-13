package org.learne.platform.learne.domain.model.commands.Question;

public record CreateQuestionCommand(Long examId, String question) {

    public CreateQuestionCommand {
        if(examId == null) {
            throw new NullPointerException("Exam id is required");
        }
        if(examId <= 0) {
            throw new IllegalArgumentException("Exam id must be a positive number");
        }
        if(question == null || question.isBlank()) {
            throw new NullPointerException("Question is required");
        }
    }
}
