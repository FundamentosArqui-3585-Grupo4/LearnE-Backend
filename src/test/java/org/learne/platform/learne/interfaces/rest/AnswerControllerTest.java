package org.learne.platform.learne.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Answer;
import org.learne.platform.learne.domain.model.aggregates.Question;
import org.learne.platform.learne.domain.model.queries.Answer.GetAllAnswersQuery;
import org.learne.platform.learne.domain.model.queries.Answer.GetAnswerByIdQuery;
import org.learne.platform.learne.domain.services.Answer.AnswerCommandService;
import org.learne.platform.learne.domain.services.Answer.AnswerQueryService;
import org.learne.platform.learne.interfaces.rest.resources.Answer.CreateAnswerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnswerCommandService commandService;

    @MockBean
    private AnswerQueryService queryService;

    private Answer testAnswer;

    @BeforeEach
    void setup() throws Exception {
        Question question = new Question(); // crea el question falso
        Field questionIdField = question.getClass().getSuperclass().getDeclaredField("id");
        questionIdField.setAccessible(true);
        questionIdField.set(question, 1L);

        testAnswer = new Answer();
        Field idField = testAnswer.getClass().getSuperclass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(testAnswer, 1L);

        // Asigna la pregunta al Answer
        Field questionField = testAnswer.getClass().getDeclaredField("question");
        questionField.setAccessible(true);
        questionField.set(testAnswer, question);
    }

    @Test
    void createAnswer_shouldReturnCreated() throws Exception {
        CreateAnswerResource resource = new CreateAnswerResource(1L, true, "Correct Answer");

        when(commandService.handle(any())).thenReturn(1L);
        when(queryService.handle(any(GetAnswerByIdQuery.class))).thenReturn(Optional.of(testAnswer));

        mockMvc.perform(post("/api/v1/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getAnswerById_shouldReturnOk() throws Exception {
        when(queryService.handle(any(GetAnswerByIdQuery.class))).thenReturn(Optional.of(testAnswer));

        mockMvc.perform(get("/api/v1/answers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getAnswerById_shouldReturnNotFound() throws Exception {
        when(queryService.handle(any(GetAnswerByIdQuery.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/answers/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllAnswers_shouldReturnOk() throws Exception {
        when(queryService.handle(any(GetAllAnswersQuery.class)))
                .thenReturn(Collections.singletonList(testAnswer));

        mockMvc.perform(get("/api/v1/answers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllAnswers_shouldReturnNotFound() throws Exception {
        when(queryService.handle(any(GetAllAnswersQuery.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/answers"))
                .andExpect(status().isNotFound());
    }
}