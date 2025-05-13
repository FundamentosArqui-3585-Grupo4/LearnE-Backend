package org.learne.platform.learne.interfaces.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.domain.model.aggregates.Exam;
import org.learne.platform.learne.domain.model.aggregates.Unit;
import org.learne.platform.learne.infrastructure.persistence.jpa.CourseRepository;
import org.learne.platform.learne.infrastructure.persistence.jpa.ExamRepository;
import org.learne.platform.learne.infrastructure.persistence.jpa.UnitRepository;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.profile.infrastructure.persistence.jpa.UserRepository;
import org.learne.platform.learne.interfaces.rest.resources.Question.CreateQuestionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class QuestionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ExamRepository examRepository;

    private Long examId;

    @BeforeEach
    void setUp() {
        // Crear usuario profesor
        User teacher = new User();
        teacher.setFirstName("Juan");
        teacher.setLastName("Pérez");
        teacher.setUsername("jperez");
        teacher.setEmail("jperez@correo.com");
        teacher.setPassword("123456");
        teacher.setType_user(1);
        teacher.setType_plan(1);
        userRepository.save(teacher);

        // Crear curso
        Course course = new Course();
        course.setTitle("Curso de Prueba");
        course.setDescription("Descripción");
        course.setLevel("Básico");
        course.setDuration("2 semanas");
        course.setPrior_knowledge("Ninguno");
        course.setPrincipal_image("imagen.jpg");
        course.setUrl_video("video.mp4");
        course.setUser(teacher);
        courseRepository.save(course);

        // Crear unidad
        Unit unit = new Unit();
        unit.setTitle("Unidad 1");
        unit.setCourses(course);
        unitRepository.save(unit);

        // Crear examen
        Exam exam = new Exam();
        exam.setTitle("Examen");
        exam.setCourse(course);
        exam.setUnit(unit);
        examRepository.save(exam);

        examId = exam.getId();
    }

    @Test
    void createQuestion_shouldReturn201() {
        String url = "/api/v1/questions";

        CreateQuestionResource resource = new CreateQuestionResource(examId, "¿Cuál es la capital de Perú?");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateQuestionResource> request = new HttpEntity<>(resource, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}