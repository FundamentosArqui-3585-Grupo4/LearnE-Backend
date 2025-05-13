package org.learne.platform.learne.interfaces.rest.resources.Payment;

public record CreatePaymentResource(String name_card, Long number_card, String expire_date,
                                    Integer security_code, String email, Long student_id) {
}
