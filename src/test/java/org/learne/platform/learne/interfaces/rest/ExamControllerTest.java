package org.learne.platform.learne.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.domain.model.aggregates.Unit;
import org.learne.platform.learne.infrastructure.persistence.jpa.CourseRepository;
import org.learne.platform.learne.infrastructure.persistence.jpa.UnitRepository;
import org.learne.platform.learne.infrastructure.persistence.jpa.ExamRepository;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.profile.infrastructure.persistence.jpa.UserRepository;
import org.learne.platform.learne.interfaces.rest.resources.Exam.CreateExamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExamControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private Long unitId;
    private Long courseId;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Teacher");
        user.setUsername("testteacher");
        user.setEmail("teacher@example.com");
        user.setPassword("password");
        user.setType_user(1);
        user.setType_plan(1);
        user = userRepository.save(user);

        Course course = new Course();
        course.setTitle("Math Course");
        course.setDescription("Basic Math");
        course.setUser(user);
        course.setLevel("Beginner");
        course.setDuration("2h");
        course.setPrior_knowledge("None");
        course.setPrincipal_image("img.jpg");
        course.setUrl_video("video.com");
        course = courseRepository.save(course);
        this.courseId = course.getId();

        Unit unit = new Unit();
        unit.setCourses(course);
        unit.setTitle("Unit 1");
        unit = unitRepository.save(unit);
        this.unitId = unit.getId();
    }

    @Test
    public void testCreateExam() throws Exception {
        CreateExamResource resource = new CreateExamResource(unitId, courseId, "First Exam");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateExamResource> request = new HttpEntity<>(resource, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/exams", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("First Exam");
    }

    @Test
    public void testGetAllExams() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/exams", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetExamById() throws Exception {
        // Primero, creamos un examen
        CreateExamResource resource = new CreateExamResource(unitId, courseId, "Second Exam");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateExamResource> request = new HttpEntity<>(resource, headers);

        ResponseEntity<String> createResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/exams", request, String.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Extraemos el ID del examen creado (opcionalmente, puedes mapear el body a un objeto)
        String responseBody = createResponse.getBody();
        assertThat(responseBody).contains("Second Exam");

        // Aquí parseamos el ID del examen desde la respuesta JSON
        Long examId = objectMapper.readTree(responseBody).get("id").asLong();

        // Ahora consultamos el examen por ID
        ResponseEntity<String> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/exams/" + examId, String.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).contains("Second Exam");
    }
}