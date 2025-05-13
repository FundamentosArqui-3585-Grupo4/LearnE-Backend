package org.learne.platform.learne.domain.model.commands.Answer;

public record CreateAnswerCommand(Long questionId, boolean isCorrect,
                                  String description) {

    public CreateAnswerCommand {
        if(questionId == null) {
            throw new NullPointerException("Question id is required");
        }
        if(questionId <= 0) {
            throw new IllegalArgumentException("Question id must be a positive number");
        }
        if(description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
    }

}
