package org.learne.platform.learne.interfaces.rest.resources.Answer;

public record CreateAnswerResource(Long question_id, boolean is_correct,
                                   String description) {
}
