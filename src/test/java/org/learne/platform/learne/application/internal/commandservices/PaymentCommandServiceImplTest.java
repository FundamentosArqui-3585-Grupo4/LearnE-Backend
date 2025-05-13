package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Payment;
import org.learne.platform.learne.domain.model.commands.CreatePaymentCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.PaymentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PaymentCommandServiceImplTest {

    private PaymentRepository paymentRepository;
    private PaymentCommandServiceImpl paymentCommandService;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        paymentCommandService = new PaymentCommandServiceImpl(paymentRepository);
    }

    @Test
    void handle_ShouldCreatePaymentSuccessfully() {
        // Arrange
        CreatePaymentCommand command = new CreatePaymentCommand(
                "John Doe", 1234567812345678L, "12/30", 123, "john@example.com", 1L);

        when(paymentRepository.existsPaymentByUserId(1L)).thenReturn(false);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Optional<Payment> result = paymentCommandService.handle(command);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNameCard()).isEqualTo("John Doe");
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void handle_ShouldThrowExceptionIfPaymentExists() {
        // Arrange
        CreatePaymentCommand command = new CreatePaymentCommand(
                "Jane Doe", 1111222233334444L, "10/28", 456, "jane@example.com", 2L);

        when(paymentRepository.existsPaymentByUserId(2L)).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> paymentCommandService.handle(command));
        verify(paymentRepository, never()).save(any());
    }
}