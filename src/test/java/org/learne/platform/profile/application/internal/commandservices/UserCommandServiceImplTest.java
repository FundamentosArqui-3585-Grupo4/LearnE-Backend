package org.learne.platform.profile.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.profile.domain.model.commands.CreateUserCommand;
import org.learne.platform.profile.domain.model.commands.UpdateUserCommand;
import org.learne.platform.profile.infrastructure.persistence.jpa.UserRepository;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCommandServiceImplTest {

    private UserRepository userRepository;
    private UserCommandServiceImpl userCommandService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userCommandService = new UserCommandServiceImpl(userRepository);
    }

    @Test
    void createUser_shouldReturnUserId() throws Exception {
        // Arrange
        CreateUserCommand command = new CreateUserCommand(
                "Mateo", "Vilchez", "mateo123", "mateo@test.com", "securePass", 1, 2
        );

        User mockSavedUser = new User(command);
        setPrivateId(mockSavedUser, 99L); // Usamos reflexión para setear el ID

        when(userRepository.existsByUsernameAndEmail("mateo123", "mateo@test.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mockSavedUser); // Retornamos user con ID

        // Act
        Long resultId = userCommandService.handle(command);

        // Assert
        assertNotNull(resultId);
        assertEquals(99L, resultId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_shouldThrowIfExists() {
        // Arrange
        CreateUserCommand command = new CreateUserCommand(
                "Mateo", "Vilchez", "mateo123", "mateo@test.com", "securePass", 1, 2
        );
        when(userRepository.existsByUsernameAndEmail("mateo123", "mateo@test.com")).thenReturn(true);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> userCommandService.handle(command));
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        // Arrange
        UpdateUserCommand command = new UpdateUserCommand(
                1L, "Teus", "Updated", "teusUpdated", "updated@email.com", "newpass", 2, 3
        );
        User existing = new User();
        setPrivateId(existing, 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenReturn(existing);

        // Act
        Optional<User> result = userCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Teus", result.get().getFirstName());
        verify(userRepository).save(existing);
    }

    @Test
    void updateUser_shouldThrowIfNotFound() {
        UpdateUserCommand command = new UpdateUserCommand(
                404L, "X", "Y", "Z", "404@email.com", "pass", 0, 0
        );

        when(userRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userCommandService.handle(command));
    }

    // Helper to set protected ID
    private void setPrivateId(User user, Long id) throws Exception {
        Field idField = user.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, id);
    }
}