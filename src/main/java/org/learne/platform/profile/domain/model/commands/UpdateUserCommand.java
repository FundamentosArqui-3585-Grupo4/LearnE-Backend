package org.learne.platform.profile.domain.model.commands;

public record UpdateUserCommand(Long userId, String firstName, String lastName, String username, String email, String password,
                                Integer typeUser, Integer type_plan) {
    public UpdateUserCommand {
        if(userId == null || userId <= 0){
            throw new IllegalArgumentException("userId is required");
        }
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
