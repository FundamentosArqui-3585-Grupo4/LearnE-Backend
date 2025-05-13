package org.learne.platform.learne.interfaces.rest;

import org.learne.platform.learne.interfaces.rest.resources.TutorialsReservated.CreateTutorialsReservatedResource;
import org.learne.platform.learne.interfaces.rest.resources.TutorialsReservated.TutorialsReservatedResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TutorialsReservatedControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void createTutorialsReservated_shouldReturn201() {
        CreateTutorialsReservatedResource resource = new CreateTutorialsReservatedResource(1L, 1L);
        HttpEntity<CreateTutorialsReservatedResource> request = new HttpEntity<>(resource, headers);

        ResponseEntity<TutorialsReservatedResource> response = restTemplate.postForEntity(
                "/api/v1/tutorials_reservated", request, TutorialsReservatedResource.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().student_id());
        assertEquals(1L, response.getBody().tutorial_id());
    }

    @Test
    void getAllTutorialsReservated_shouldReturn200Or404() {
        ResponseEntity<TutorialsReservatedResource[]> response = restTemplate.getForEntity(
                "/api/v1/tutorials_reservated", TutorialsReservatedResource[].class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
}