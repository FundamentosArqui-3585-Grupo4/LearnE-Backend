package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Exam;
import org.learne.platform.learne.domain.model.commands.Exam.CreateExamCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.ExamRepository;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamCommandServiceImplTest {

    private ExamRepository examRepository;
    private ExamCommandServiceImpl examCommandService;

    @BeforeEach
    void setUp() {
        examRepository = mock(ExamRepository.class);
        examCommandService = new ExamCommandServiceImpl(examRepository);
    }

    @Test
    void handle_shouldCreateExamSuccessfully() {
        CreateExamCommand command = new CreateExamCommand(1L, 2L, "Final Exam");
        Exam exam = new Exam(command);
        setPrivateId(exam, 99L); // Setea ID mockeado correctamente

        when(examRepository.existsByCourseIdAndTitle(2L, "Final Exam")).thenReturn(false);
        when(examRepository.save(any(Exam.class))).thenAnswer(invocation -> {
            Exam e = invocation.getArgument(0);
            setPrivateId(e, 99L); // 💡 Setea el ID antes de devolver
            return e;
        });

        Long result = examCommandService.handle(command);

        assertNotNull(result);
        assertEquals(99L, result);
        verify(examRepository).save(any(Exam.class));
    }

    @Test
    void handle_shouldThrowIfExamExists() {
        // Arrange
        CreateExamCommand command = new CreateExamCommand(1L, 2L, "Duplicate Exam");
        when(examRepository.existsByCourseIdAndTitle(2L, "Duplicate Exam")).thenReturn(true);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> examCommandService.handle(command));
    }

    // 👇 Helper para poder setear el ID usando reflexión
    private void setPrivateId(Object entity, Long id) {
        try {
            Field idField = entity.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set ID via reflection", e);
        }
    }
}