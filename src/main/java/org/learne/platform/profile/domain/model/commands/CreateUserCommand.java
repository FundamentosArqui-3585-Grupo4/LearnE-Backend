package org.learne.platform.profile.domain.model.commands;

public record CreateUserCommand(String firstName, String lastName, String username, String email, String password,
                                Integer typeUser, Integer type_plan) {
    public CreateUserCommand {
        if(firstName == null || firstName.isEmpty()){
            throw new IllegalArgumentException("firstName is required");
        }
        if(lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException("lastName is required");
        }
        if(username == null || username.isEmpty()){
            throw new IllegalArgumentException("username is required");
        }
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException("email is required");
        }
        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("password is required");
        }
    }
}
