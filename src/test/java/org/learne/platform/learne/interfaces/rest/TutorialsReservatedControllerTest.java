package org.learne.platform.learne.interfaces.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.domain.model.aggregates.TutorialsCourses;
import org.learne.platform.learne.infrastructure.persistence.jpa.CourseRepository;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsCoursesRepository;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsReservated.CreateTutorialsReservatedResource;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsReservated.TutorialsReservatedResource;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.profile.infrastructure.persistence.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TutorialsReservatedControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TutorialsCoursesRepository tutorialsCoursesRepository;

    private Long studentId;
    private Long tutorialId;
    private HttpHeaders headers;

    @BeforeEach
    void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Student
        User student = new User();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setUsername("johndoe");
        student.setEmail("john@example.com");
        student.setPassword("123456");
        student.setType_user(2);
        student.setType_plan(1);
        studentId = userRepository.save(student).getId();

        // Teacher
        User teacher = new User();
        teacher.setFirstName("Jane");
        teacher.setLastName("Smith");
        teacher.setUsername("janesmith");
        teacher.setEmail("jane@example.com");
        teacher.setPassword("123456");
        teacher.setType_user(1);
        teacher.setType_plan(1);
        teacher = userRepository.save(teacher);

        // Course
        Course course = new Course();
        course.setTitle("Sample Course");
        course.setDescription("Test course");
        course.setUser(teacher);
        course.setLevel("Beginner");
        course.setDuration("1h");
        course.setPrior_knowledge("None");
        course.setPrincipal_image("image.jpg");
        course.setUrl_video("video.com");
        course = courseRepository.save(course);

        // Tutorial
        TutorialsCourses tutorial = new TutorialsCourses();
        tutorial.setCourse(course);
        tutorial.setUser(teacher);
        tutorial.setDate("2025-12-01");
        tutorial.setHour("10:00");
        tutorial.setIsReservated(false);
        tutorial.setLink("https://zoom.us");
        tutorialId = tutorialsCoursesRepository.save(tutorial).getId();
    }

    @Test
    void createTutorialsReservated_shouldReturn201() {
        CreateTutorialsReservatedResource resource = new CreateTutorialsReservatedResource(studentId, tutorialId);
        HttpEntity<CreateTutorialsReservatedResource> request = new HttpEntity<>(resource, headers);

        ResponseEntity<TutorialsReservatedResource> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/tutorials_reservated", request, TutorialsReservatedResource.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(studentId, response.getBody().student_id());
        assertEquals(tutorialId, response.getBody().tutorial_id());
    }

    @Test
    void getAllTutorialsReservated_shouldReturn200() {
        // Crear al menos una reservación
        createTutorialsReservated_shouldReturn201();

        ResponseEntity<TutorialsReservatedResource[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/tutorials_reservated", TutorialsReservatedResource[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<TutorialsReservatedResource> list = Arrays.asList(response.getBody());
        assertFalse(list.isEmpty());
    }
}