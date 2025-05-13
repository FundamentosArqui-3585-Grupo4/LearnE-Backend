package org.learne.platform.learne.interfaces.rest.transform.Payment;

import org.learne.platform.learne.domain.model.aggregates.Payment;
import org.learne.platform.learne.interfaces.rest.resources.Payment.PaymentResource;

public class PaymentResourceFromEntityAssembler {

    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(
                entity.getId(),
                entity.getNameCard(),
                entity.getNumberCard(),
                entity.getExpireDate(),
                entity.getSecurityCode(),
                entity.getEmailAddress().email(),
                entity.getUser().getId()
        );
    }
}
