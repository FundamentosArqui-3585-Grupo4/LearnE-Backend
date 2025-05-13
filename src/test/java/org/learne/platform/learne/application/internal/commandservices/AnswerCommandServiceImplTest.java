package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Answer;
import org.learne.platform.learne.domain.model.commands.Answer.CreateAnswerCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.AnswerRepository;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnswerCommandServiceImplTest {

    private void setPrivateId(Answer answer, Long id) {
        try {
            Field idField = answer.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(answer, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AnswerRepository answerRepository;
    private AnswerCommandServiceImpl answerCommandService;

    @BeforeEach
    void setUp() {
        answerRepository = mock(AnswerRepository.class);
        answerCommandService = new AnswerCommandServiceImpl(answerRepository);
    }

    @Test
    void handle_shouldCreateAnswerSuccessfully() {
        // Arrange
        CreateAnswerCommand command = new CreateAnswerCommand(1L, true, "Answer A");

        when(answerRepository.existsByQuestionIdAndDescription(1L, "Answer A")).thenReturn(false);
        when(answerRepository.existsByQuestionIdAndIsCorrect(1L, true)).thenReturn(false);

        when(answerRepository.save(any(Answer.class))).thenAnswer(invocation -> {
            Answer savedAnswer = invocation.getArgument(0);
            setPrivateId(savedAnswer, 99L);
            return savedAnswer;
        });

        // Act
        Long result = answerCommandService.handle(command);

        // Assert
        assertNotNull(result);
        assertEquals(99L, result);
        verify(answerRepository).save(any(Answer.class));
    }

    @Test
    void handle_shouldThrowIfDescriptionExists() {
        CreateAnswerCommand command = new CreateAnswerCommand(1L, false,"Answer A");

        when(answerRepository.existsByQuestionIdAndDescription(1L, "Answer A")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> answerCommandService.handle(command));
    }

    @Test
    void handle_shouldThrowIfCorrectAnswerExists() {
        CreateAnswerCommand command = new CreateAnswerCommand(1L, true,"Another A" );

        when(answerRepository.existsByQuestionIdAndDescription(1L, "Another A")).thenReturn(false);
        when(answerRepository.existsByQuestionIdAndIsCorrect(1L, true)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> answerCommandService.handle(command));
    }
}