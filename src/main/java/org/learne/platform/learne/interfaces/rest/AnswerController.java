package org.learne.platform.learne.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.domain.model.queries.Answer.GetAllAnswersQuery;
import org.learne.platform.learne.domain.model.queries.Answer.GetAnswerByIdQuery;
import org.learne.platform.learne.domain.services.Answer.AnswerCommandService;
import org.learne.platform.learne.domain.services.Answer.AnswerQueryService;
import org.learne.platform.learne.interfaces.rest.resources.Answer.AnswerResource;
import org.learne.platform.learne.interfaces.rest.resources.Answer.CreateAnswerResource;
import org.learne.platform.learne.interfaces.rest.transform.Answer.AnswerResourceFromEntityAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Answer.CreateAnswerCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/answers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Answers", description = "Answers API")
public class AnswerController {

    private final AnswerCommandService answerCommandService;
    private final AnswerQueryService answerQueryService;

    public AnswerController(AnswerCommandService answerCommandService, AnswerQueryService answerQueryService) {
        this.answerCommandService = answerCommandService;
        this.answerQueryService = answerQueryService;
    }

    @PostMapping
    @Operation(summary = "Create answer", description = "Create answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Answer created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Answer not found")
    })
    public ResponseEntity<AnswerResource> createAnswer(@RequestBody CreateAnswerResource resource) {
        var createAnswerCommand = CreateAnswerCommandFromResourceAssembler.ToCommandFromResource(resource);
        var answerId = answerCommandService.handle(createAnswerCommand);

        if(answerId == null || answerId <= 0L) {
            return ResponseEntity.badRequest().build();
        }

        var getAnswerByIdQuery = new GetAnswerByIdQuery(answerId);
        var answer = answerQueryService.handle(getAnswerByIdQuery);

        if(answer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var answerEntity = answer.get();
        var answerResource = AnswerResourceFromEntityAssembler.ToResourceFromEntity(answerEntity);

        return new ResponseEntity<>(answerResource, HttpStatus.CREATED);
    }

    @GetMapping("/{answerId}")
    @Operation(summary = "Get answer by id", description = "Get answer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answer found"),
            @ApiResponse(responseCode = "404", description = "Answer not found")
    })
    public ResponseEntity<AnswerResource> getAnswerById(@PathVariable Long answerId) {
        var getAnswerByIdQuery = new GetAnswerByIdQuery(answerId);
        var answer = answerQueryService.handle(getAnswerByIdQuery);
        if (answer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var answerEntity = answer.get();
        var answerResource = AnswerResourceFromEntityAssembler.ToResourceFromEntity(answerEntity);
        return ResponseEntity.ok(answerResource);
    }

    @GetMapping
    @Operation(summary = "Get all answers", description = "Get all answers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answers found"),
            @ApiResponse(responseCode = "404", description = "Answers not found")
    })
    public ResponseEntity<List<AnswerResource>> getAllAnswers() {
        var answers = answerQueryService.handle(new GetAllAnswersQuery());
        if (answers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var answerResources = answers.stream()
                .map(AnswerResourceFromEntityAssembler:: ToResourceFromEntity)
                .toList();
        return ResponseEntity.ok(answerResources);
    }
}
