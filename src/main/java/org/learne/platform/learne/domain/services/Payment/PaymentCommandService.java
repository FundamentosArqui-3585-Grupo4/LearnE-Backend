package org.learne.platform.learne.domain.services.Payment;

import org.learne.platform.learne.domain.model.aggregates.Payment;
import org.learne.platform.learne.domain.model.commands.CreatePaymentCommand;
import org.learne.platform.learne.domain.model.commands.DeletePaymentCommand;
import org.learne.platform.learne.domain.model.commands.UpdatePaymentCommand;

import java.util.Optional;

public interface PaymentCommandService {
    Optional<Payment> handle(CreatePaymentCommand command);
}
