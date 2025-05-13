package org.learne.platform.learne.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.learne.platform.learne.interfaces.rest.resources.CoursesEnrollment.CreateCoursesEnrollmentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CoursesEnrollmentControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private JdbcTemplate jdbcTemplate;

    private CreateCoursesEnrollmentResource resource;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("""
            INSERT IGNORE INTO users (id, first_name, last_name, username, email, password)
            VALUES (1, 'Mateo', 'Vilchez', 'mateo123', 'mateo@test.com', 'pass')
        """);
        jdbcTemplate.update("""
            INSERT IGNORE INTO courses (id, title, description, teacher_id, level, duration, prior_knowledge, principal_image, url_video)
            VALUES (1, 'Test Course', 'Desc', 1, 'Basic', '2h', 'None', 'img.jpg', 'video.mp4')
        """);

        resource = new CreateCoursesEnrollmentResource(1L, 1L);
    }

    @Test
    void createEnrollment_shouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/v1/courses_enrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.student_id").value(1))
                .andExpect(jsonPath("$.course_id").value(1));
    }

    @Test
    void getAllCoursesEnrollment_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/courses_enrollment"))
                .andExpect(status().isOk());
    }
}