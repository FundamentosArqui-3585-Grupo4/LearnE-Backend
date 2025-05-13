package org.learne.platform.learne.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.learne.domain.model.commands.CreatePaymentCommand;
import org.learne.platform.learne.domain.model.valueobjects.EmailAddress;
import org.learne.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
public class Payment extends AuditableAbstractAggregateRoot<Payment> {

    @Column(nullable = false)
    private String nameCard;

    @Column(nullable = false)
    private Long numberCard;

    @Column(nullable = false)
    private String expireDate;

    @Column(nullable = false)
    private Integer securityCode;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email"))
    })
    private EmailAddress emailAddress;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User user;

    public Payment() {}

    public Payment(CreatePaymentCommand command) {
        this.nameCard = command.nameCard();
        this.numberCard = command.numberCard();
        this.expireDate = command.expireDate();
        this.securityCode = command.securityCode();
        this.emailAddress = new EmailAddress(command.emailAddress());
        this.user = new User(command.studentId());
    }
}
