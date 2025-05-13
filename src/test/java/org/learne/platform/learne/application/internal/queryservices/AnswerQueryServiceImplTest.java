package org.learne.platform.learne.application.internal.queryservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Answer;
import org.learne.platform.learne.domain.model.queries.Answer.GetAllAnswersQuery;
import org.learne.platform.learne.domain.model.queries.Answer.GetAnswerByIdQuery;
import org.learne.platform.learne.infrastructure.persistence.jpa.AnswerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnswerQueryServiceImplTest {

    private AnswerRepository answerRepository;
    private AnswerQueryServiceImpl answerQueryService;

    @BeforeEach
    void setUp() {
        answerRepository = mock(AnswerRepository.class);
        answerQueryService = new AnswerQueryServiceImpl(answerRepository);
    }

    @Test
    void handle_shouldReturnAnswerById() {
        Answer answer = mock(Answer.class);
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));

        var result = answerQueryService.handle(new GetAnswerByIdQuery(1L));

        assertTrue(result.isPresent());
        assertEquals(answer, result.get());
    }

    @Test
    void handle_shouldReturnAllAnswers() {
        List<Answer> answers = List.of(mock(Answer.class));
        when(answerRepository.findAll()).thenReturn(answers);

        var result = answerQueryService.handle(new GetAllAnswersQuery());

        assertEquals(1, result.size());
    }
}