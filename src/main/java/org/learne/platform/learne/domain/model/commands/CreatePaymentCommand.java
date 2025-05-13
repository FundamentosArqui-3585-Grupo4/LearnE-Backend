package org.learne.platform.learne.domain.model.commands;

public record CreatePaymentCommand(String nameCard, Long numberCard, String expireDate, Integer securityCode, String emailAddress, Long studentId) {
    public CreatePaymentCommand{
        if (studentId == null || studentId <= 0){
            throw new IllegalArgumentException("Student ID invalid");
        }
        if (nameCard == null || nameCard.isBlank()){
            throw new IllegalArgumentException("Name Card is required");
        }
        if (numberCard == null || numberCard <= 0){
            throw new IllegalArgumentException("Number Card invalid");
        }
        if (expireDate == null || expireDate.isBlank()){
            throw new IllegalArgumentException("Expire Date is required");
        }
        if(securityCode == null || securityCode <= 0){
            throw new IllegalArgumentException("Security Code invalid");
        }
        if (emailAddress == null || emailAddress.isBlank()){
            throw new IllegalArgumentException("Email Address is required");
        }
    }

  
}
