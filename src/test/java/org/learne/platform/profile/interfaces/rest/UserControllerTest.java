package org.learne.platform.profile.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.profile.interfaces.rest.resources.CreateUserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CreateUserResource testUser;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("""
            INSERT INTO users (id, first_name, last_name, username, email, password, type_user, type_plan, created_at, updated_at)
            VALUES (1, 'Mateo', 'Vilchez', 'mateo123', 'mateo@test.com', 'securePass', 1, 2, NOW(), NOW())
        """);

        testUser = new CreateUserResource(
                "Mateo",
                "Vilchez",
                "mateo123",
                "mateo@test.com",
                "securePass",
                1,
                2
        );
    }

    @Test
    @Rollback
    void createUser_shouldReturnCreated() throws Exception {
        CreateUserResource newUser = new CreateUserResource(
                "Luis",
                "Torres",
                "luisT",
                "luis@test.com",
                "123456",
                2,
                1
        );

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("luis@test.com"))
                .andExpect(jsonPath("$.username").value("luisT"));
    }

    @Test
    void createUser_shouldFailIfDuplicate() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("username and email already exists")));
    }

    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].username").value("mateo123"));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("mateo@test.com"));
    }

    @Test
    void getUserById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users/999"))
                .andExpect(status().isNotFound());
    }
}