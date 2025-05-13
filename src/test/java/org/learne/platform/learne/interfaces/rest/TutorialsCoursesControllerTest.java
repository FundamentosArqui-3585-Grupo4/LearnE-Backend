package org.learne.platform.learne.interfaces.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.infrastructure.persistence.jpa.CourseRepository;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsCoursesRepository;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses.CreateTutorialsCoursesResource;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses.TutorialsCoursesResource;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses.UpdateTutorialsCoursesResource;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.profile.infrastructure.persistence.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TutorialsCoursesControllerTest {

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

    private Long courseId;
    private Long teacherId;
    private HttpHeaders headers;

    @BeforeEach
    void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Teacher
        User teacher = new User();
        teacher.setFirstName("Prof");
        teacher.setLastName("Smith");
        teacher.setUsername("profsmith");
        teacher.setEmail("prof@example.com");
        teacher.setPassword("securepass");
        teacher.setType_user(1);
        teacher.setType_plan(1);
        teacherId = userRepository.save(teacher).getId();

        // Course
        Course course = new Course();
        course.setTitle("Math Advanced");
        course.setDescription("Level 2");
        course.setUser(teacher);
        course.setLevel("Intermediate");
        course.setDuration("2h");
        course.setPrior_knowledge("Basic Math");
        course.setPrincipal_image("img.jpg");
        course.setUrl_video("mathvideo.com");
        courseId = courseRepository.save(course).getId();
    }

    @Test
    void createTutorialsCourses_shouldReturn201() {
        CreateTutorialsCoursesResource resource = new CreateTutorialsCoursesResource(
                courseId, teacherId, "2025-12-01", "10:45", false, "https://zoom.us/1"
        );

        HttpEntity<CreateTutorialsCoursesResource> request = new HttpEntity<>(resource, headers);
        ResponseEntity<TutorialsCoursesResource> response = restTemplate.postForEntity(
                getBaseUrl(), request, TutorialsCoursesResource.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(courseId, response.getBody().courses_id());
        assertEquals(teacherId, response.getBody().teacher_id());
    }

    @Test
    void getAllTutorialsCourses_shouldReturn200() {
        createTutorial(); // asegúrate de que hay al menos uno

        ResponseEntity<TutorialsCoursesResource[]> response = restTemplate.getForEntity(
                getBaseUrl(), TutorialsCoursesResource[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void getTutorialsCourseById_shouldReturn200() {
        Long tutorialId = createTutorial();

        ResponseEntity<TutorialsCoursesResource> response = restTemplate.getForEntity(
                getBaseUrl() + "/" + tutorialId, TutorialsCoursesResource.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tutorialId, response.getBody().id());
    }

    @Test
    void updateTutorialCourse_shouldReturn200() {
        Long tutorialId = createTutorial();

        UpdateTutorialsCoursesResource update = new UpdateTutorialsCoursesResource(1L, 2L, "2025-12-02", "11:00", true,"https://zoom.us/updated"
        );

        HttpEntity<UpdateTutorialsCoursesResource> request = new HttpEntity<>(update, headers);
        ResponseEntity<TutorialsCoursesResource> response = restTemplate.exchange(
                getBaseUrl() + "/" + tutorialId,
                HttpMethod.PUT,
                request,
                TutorialsCoursesResource.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("2025-12-02", response.getBody().date());
        assertEquals("11:00", response.getBody().hour());
        assertTrue(response.getBody().is_reservated());
    }

    private Long createTutorial() {
        CreateTutorialsCoursesResource resource = new CreateTutorialsCoursesResource(
                courseId, teacherId, "2025-12-01", "10:00", false, "https://zoom.us/test"
        );

        HttpEntity<CreateTutorialsCoursesResource> request = new HttpEntity<>(resource, headers);
        ResponseEntity<TutorialsCoursesResource> response = restTemplate.postForEntity(
                getBaseUrl(), request, TutorialsCoursesResource.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        return response.getBody().id();
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1/tutorials_courses";
    }
}