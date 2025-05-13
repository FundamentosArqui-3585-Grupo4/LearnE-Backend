package org.learne.platform.learne.interfaces.rest.transform.Payment;

import org.learne.platform.learne.domain.model.commands.CreatePaymentCommand;
import org.learne.platform.learne.interfaces.rest.resources.Payment.CreatePaymentResource;

public class CreatePaymentCommandFromResourceAssembler {
    public static CreatePaymentCommand toCommandFromResource(CreatePaymentResource resource) {
        return new CreatePaymentCommand(
                resource.name_card(),
                resource.number_card(),
                resource.expire_date(),
                resource.security_code(),
                resource.email(),
                resource.student_id()
        );
    }
}
