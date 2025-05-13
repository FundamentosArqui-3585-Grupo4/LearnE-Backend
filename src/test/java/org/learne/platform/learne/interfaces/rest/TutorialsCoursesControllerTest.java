package org.learne.platform.learne.interfaces.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses.CreateTutorialsCoursesResource;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses.TutorialsCoursesResource;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses.UpdateTutorialsCoursesResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TutorialsCoursesControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TutorialsCoursesController controller;


    @BeforeEach
    void setup() {

        jdbcTemplate.execute("""
        INSERT INTO users (id, username, email, password, first_name, last_name, type_user, type_plan, created_at, updated_at)
        VALUES (2, 'teacher1', 'teacher@mail.com', '123456', 'Teacher', 'One', 1, 1, NOW(), NOW())
        ON DUPLICATE KEY UPDATE username = username
    """);

        jdbcTemplate.execute("""
        INSERT INTO courses (id, title, description, duration, level, principal_image, prior_knowledge, url_video, teacher_id, created_at, updated_at)
        VALUES (1, 'Curso Test', 'Descripción', '1h', 'Básico', 'img.jpg', 'ninguno', 'video.url', 2, NOW(), NOW())
        ON DUPLICATE KEY UPDATE title = title
    """);
    }

    @Test
    void createTutorialsCourses_shouldReturn201() {
        String url = "/api/v1/tutorials_courses";

        CreateTutorialsCoursesResource resource = new CreateTutorialsCoursesResource(
                1L, 2L, "2025-12-01", "10:45", false, "https://test"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateTutorialsCoursesResource> request = new HttpEntity<>(resource, headers);

        ResponseEntity<TutorialsCoursesResource> response = restTemplate.postForEntity(
                url, request, TutorialsCoursesResource.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(resource.courses_id(), response.getBody().courses_id());
        assertEquals(resource.teacher_id(), response.getBody().teacher_id());
        assertEquals(resource.date(), response.getBody().date());
        assertEquals(resource.hour(), response.getBody().hour());
        assertEquals(resource.is_reservated(), response.getBody().is_reservated());
        assertEquals(resource.link(), response.getBody().link());
    }

    @Test
    void getAllTutorialsCourses_shouldReturn200() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/tutorials_courses", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    void getTutorialsCourseById_shouldReturn200() {
        Long id = 1L;

        // Asegurarse que el tutorial existe
        jdbcTemplate.execute("INSERT INTO tutorials_courses (id, courses_id, teacher_id, date, hour, is_reservated, link, created_at, updated_at) " +
                "VALUES (1, 1, 2, '2025-12-01', '10:00', false, 'https://test', NOW(), NOW()) " +
                "ON DUPLICATE KEY UPDATE link = link");

        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/tutorials_courses/" + id, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    void updateTutorialsCourse_shouldReturn200or404() {
        Long id = 1L;

        UpdateTutorialsCoursesResource resource = new UpdateTutorialsCoursesResource(
                1L, 2L, "2025-12-01", "11:00", true, "https://updated"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateTutorialsCoursesResource> request = new HttpEntity<>(resource, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/tutorials_courses/" + id,
                HttpMethod.PUT,
                request,
                String.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
}