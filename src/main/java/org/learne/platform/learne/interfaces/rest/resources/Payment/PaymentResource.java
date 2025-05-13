package org.learne.platform.learne.interfaces.rest.resources.Payment;

public record PaymentResource(Long id, String name_card, Long number_card, String expire_date,
                              Integer security_code, String email, Long student_id) {
}
