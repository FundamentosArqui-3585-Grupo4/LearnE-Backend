package org.learne.platform.profile.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses.TutorialsCoursesResource;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses.UpdateTutorialsCoursesResource;
import org.learne.platform.learne.interfaces.rest.transform.TutorialsCourses.TutorialsCoursesResourceFromEntityAssembler;
import org.learne.platform.learne.interfaces.rest.transform.TutorialsCourses.UpdateTutorialsCoursesCommandFromResourceAssembler;
import org.learne.platform.profile.domain.model.queries.GetAllUsersQuery;
import org.learne.platform.profile.domain.model.queries.GetUserByIdQuery;
import org.learne.platform.profile.domain.services.UserCommandService;
import org.learne.platform.profile.domain.services.UserQueryService;
import org.learne.platform.profile.interfaces.rest.resources.CreateUserResource;
import org.learne.platform.profile.interfaces.rest.resources.UpdateUserResource;
import org.learne.platform.profile.interfaces.rest.resources.UserResource;
import org.learne.platform.profile.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import org.learne.platform.profile.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;
import org.learne.platform.profile.interfaces.rest.transform.UserResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Users API")
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    public UserController(UserCommandService userCommandService,UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }
    @PostMapping
    @Operation(summary = "Create User", description = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> createExam(@RequestBody CreateUserResource resource) {
        var createUserCommand = CreateUserCommandFromResourceAssembler.ToCommandFromResource(resource);
        var userId = userCommandService.handle(createUserCommand);

        if(userId == null || userId <= 0L) {
            return ResponseEntity.badRequest().build();
        }

        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);

        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userEntity = user.get();
        var userResource = UserResourceFromEntityAssembler.ToResourceFromEntity(userEntity);

        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id", description = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userEntity = user.get();
        var userResource = UserResourceFromEntityAssembler.ToResourceFromEntity(userEntity);
        return ResponseEntity.ok(userResource);
    }
    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "Users not found")
    })
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var users = userQueryService.handle(new GetAllUsersQuery());
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var usersResource = users.stream()
                .map(UserResourceFromEntityAssembler:: ToResourceFromEntity)
                .toList();
        return ResponseEntity.ok(usersResource);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update Users", description = "Update users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users updated"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<UserResource> updateUser(@PathVariable Long id,
                                                                         @RequestBody UpdateUserResource resource) {
        var updateUserCommand = UpdateUserCommandFromResourceAssembler.ToCommandFromResource(id, resource);
        var updateUser = userCommandService.handle(updateUserCommand);
        if (updateUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedUserEntity = updateUser.get();
        var updatedUserResource = UserResourceFromEntityAssembler.ToResourceFromEntity(updatedUserEntity);
        return ResponseEntity.ok(updatedUserResource);
    }
}
