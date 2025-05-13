package org.learne.platform.profile.interfaces.rest.resources;

public record UserResource(Long id, String firstName, String lastName, String username, String email,
                           String password, Integer type_user, Integer type_plan ) {
}
