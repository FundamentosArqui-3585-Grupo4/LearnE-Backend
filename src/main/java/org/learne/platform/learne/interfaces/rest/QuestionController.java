package org.learne.platform.learne.interfaces.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.domain.model.queries.Question.GetAllQuestionsQuery;
import org.learne.platform.learne.domain.model.queries.Question.GetQuestionByIdQuery;
import org.learne.platform.learne.domain.services.Question.QuestionCommandService;
import org.learne.platform.learne.domain.services.Question.QuestionQueryService;
import org.learne.platform.learne.interfaces.rest.resources.Question.CreateQuestionResource;
import org.learne.platform.learne.interfaces.rest.resources.Question.QuestionResource;
import org.learne.platform.learne.interfaces.rest.transform.Question.CreateQuestionCommandFromResourceAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Question.QuestionResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/questions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Questions", description = "Questions API")
public class QuestionController {

    private final QuestionCommandService questionCommandService;
    private final QuestionQueryService questionQueryService;


    public QuestionController(QuestionCommandService questionCommandService, QuestionQueryService questionQueryService) {
        this.questionCommandService = questionCommandService;
        this.questionQueryService = questionQueryService;
    }


    @PostMapping
    @Operation(summary = "Create question", description = "Create question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Question created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Question not found")
    })
    public ResponseEntity<QuestionResource> createQuestion(@RequestBody CreateQuestionResource resource) {
        var createQuestionCommand = CreateQuestionCommandFromResourceAssembler.ToCommandFromResource(resource);
        var questionId = questionCommandService.handle(createQuestionCommand);

        if(questionId == null || questionId <= 0L) {
            return ResponseEntity.badRequest().build();
        }

        var getQuestionByIdQuery = new GetQuestionByIdQuery(questionId);
        var question = questionQueryService.handle(getQuestionByIdQuery);

        if(question.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var questionEntity = question.get();
        var questionResource = QuestionResourceFromEntityAssembler.ToResourceFromEntity(questionEntity);

        return new ResponseEntity<>(questionResource, HttpStatus.CREATED);
    }



    @GetMapping("/{questionId}")
    @Operation(summary = "Get question by id", description = "Get question by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question found"),
            @ApiResponse(responseCode = "404", description = "Question not found")
    })
    public ResponseEntity<QuestionResource> getQuestionById(@PathVariable Long questionId) {
        var getQuestionByIdQuery = new GetQuestionByIdQuery(questionId);
        var question = questionQueryService.handle(getQuestionByIdQuery);
        if (question.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var questionEntity = question.get();
        var questionResource = QuestionResourceFromEntityAssembler.ToResourceFromEntity(questionEntity);
        return ResponseEntity.ok(questionResource);
    }


    @GetMapping
    @Operation(summary = "Get all questions", description = "Get all questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question found"),
            @ApiResponse(responseCode = "404", description = "Question not found")
    })
    public ResponseEntity<List<QuestionResource>> getAllQuestions() {
        var questions = questionQueryService.handle(new GetAllQuestionsQuery());
        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var questionResources = questions.stream()
                .map(QuestionResourceFromEntityAssembler:: ToResourceFromEntity)
                .toList();
        return ResponseEntity.ok(questionResources);
    }
}
