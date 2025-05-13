package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Question;
import org.learne.platform.learne.domain.model.commands.Question.CreateQuestionCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.QuestionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionCommandServiceImplTest {

    private QuestionRepository questionRepository;
    private QuestionCommandServiceImpl questionCommandService;

    @BeforeEach
    void setUp() {
        questionRepository = mock(QuestionRepository.class);
        questionCommandService = new QuestionCommandServiceImpl(questionRepository);
    }

    @Test
    void handle_shouldCreateQuestionSuccessfully() {
        // Arrange
        CreateQuestionCommand command = new CreateQuestionCommand(1L, "¿Cuál es la capital de Perú?");

        when(questionRepository.existsByExamIdAndQuestion(1L, "¿Cuál es la capital de Perú?")).thenReturn(false);
        when(questionRepository.save(any(Question.class))).thenAnswer(invocation -> {
            Question q = invocation.getArgument(0);
            q.setId(99L);
            return q;
        });

        // Act
        Long questionId = questionCommandService.handle(command);

        // Assert
        assertNotNull(questionId);
        assertEquals(99L, questionId);
    }
}