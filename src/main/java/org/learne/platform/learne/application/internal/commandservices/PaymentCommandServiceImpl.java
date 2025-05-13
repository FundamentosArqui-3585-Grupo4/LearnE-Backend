package org.learne.platform.learne.application.internal.commandservices;

import org.learne.platform.learne.domain.model.aggregates.Payment;
import org.learne.platform.learne.domain.model.commands.CreatePaymentCommand;
import org.learne.platform.learne.domain.model.commands.DeletePaymentCommand;
import org.learne.platform.learne.domain.model.commands.UpdatePaymentCommand;
import org.learne.platform.learne.domain.services.Payment.PaymentCommandService;
import org.learne.platform.learne.infrastructure.persistence.jpa.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {
    private final PaymentRepository paymentRepository;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Optional<Payment> handle(CreatePaymentCommand command) {
        if (paymentRepository.existsPaymentByUserId(command.studentId())){
            throw new IllegalArgumentException("Payment for student already exists");
        }
        var payment = new Payment(command);
        paymentRepository.save(payment);
        return Optional.of(payment);
    }
}
