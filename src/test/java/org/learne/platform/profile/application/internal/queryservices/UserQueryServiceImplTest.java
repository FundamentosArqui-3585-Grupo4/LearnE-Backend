package org.learne.platform.profile.application.internal.queryservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.profile.domain.model.queries.GetAllUsersQuery;
import org.learne.platform.profile.domain.model.queries.GetUserByIdQuery;
import org.learne.platform.profile.infrastructure.persistence.jpa.UserRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserQueryServiceImplTest {

    private UserRepository userRepository;
    private UserQueryServiceImpl userQueryService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userQueryService = new UserQueryServiceImpl(userRepository);
    }

    @Test
    void handle_getUserById_shouldReturnUser() throws Exception {
        // Arrange
        User user = new User();
        setPrivateId(user, 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userQueryService.handle(new GetUserByIdQuery(1L));

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void handle_getUserById_shouldReturnEmptyIfNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<User> result = userQueryService.handle(new GetUserByIdQuery(999L));

        assertTrue(result.isEmpty());
    }

    @Test
    void handle_getAllUsers_shouldReturnListOfUsers() throws Exception {
        User user1 = new User();
        User user2 = new User();
        setPrivateId(user1, 1L);
        setPrivateId(user2, 2L);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userQueryService.handle(new GetAllUsersQuery());

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    // Helper para asignar ID en entidad
    private void setPrivateId(User user, Long id) throws Exception {
        Field idField = user.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, id);
    }
}