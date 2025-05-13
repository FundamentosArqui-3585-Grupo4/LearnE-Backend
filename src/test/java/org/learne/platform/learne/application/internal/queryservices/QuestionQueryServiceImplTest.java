package org.learne.platform.learne.application.internal.queryservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Question;
import org.learne.platform.learne.domain.model.queries.Question.GetAllQuestionsQuery;
import org.learne.platform.learne.domain.model.queries.Question.GetQuestionByIdQuery;
import org.learne.platform.learne.infrastructure.persistence.jpa.QuestionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuestionQueryServiceImplTest {

    private QuestionRepository questionRepository;
    private QuestionQueryServiceImpl questionQueryService;

    @BeforeEach
    void setUp() {
        questionRepository = mock(QuestionRepository.class);
        questionQueryService = new QuestionQueryServiceImpl(questionRepository);
    }

    @Test
    void handle_shouldReturnQuestionById() {
        // Arrange
        Question question = new Question(1L);
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        // Act
        var result = questionQueryService.handle(new GetQuestionByIdQuery(1L));

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void handle_shouldReturnAllQuestions() {
        // Arrange
        List<Question> questions = List.of(new Question(1L), new Question(2L));
        when(questionRepository.findAll()).thenReturn(questions);

        // Act
        var result = questionQueryService.handle(new GetAllQuestionsQuery());

        // Assert
        assertEquals(2, result.size());
    }
}