package org.learne.platform.learne.interfaces.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.domain.model.queries.Exam.GetAllExamsQuery;
import org.learne.platform.learne.domain.model.queries.Exam.GetExamByIdQuery;
import org.learne.platform.learne.domain.services.Exam.ExamCommandService;
import org.learne.platform.learne.domain.services.Exam.ExamQueryService;
import org.learne.platform.learne.interfaces.rest.resources.Exam.CreateExamResource;
import org.learne.platform.learne.interfaces.rest.resources.Exam.ExamResource;
import org.learne.platform.learne.interfaces.rest.transform.Exam.CreateExamCommandFromResourceAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Exam.ExamResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/exams", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Exams", description = "Exams API")
public class ExamController {

    private final ExamCommandService examCommandService;
    private final ExamQueryService examQueryService;

    public ExamController(ExamCommandService examCommandService, ExamQueryService examQueryService) {
        this.examCommandService = examCommandService;
        this.examQueryService = examQueryService;
    }

    @PostMapping
    @Operation(summary = "Create exam", description = "Create exam")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exam created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Exam not found")
    })
    public ResponseEntity<ExamResource> createExam(@RequestBody CreateExamResource resource) {
        var createExamCommand = CreateExamCommandFromResourceAssembler.ToCommandFromResource(resource);
        var examId = examCommandService.handle(createExamCommand);

        if(examId == null || examId <= 0L) {
            return ResponseEntity.badRequest().build();
        }

        var getExamByIdQuery = new GetExamByIdQuery(examId);
        var exam = examQueryService.handle(getExamByIdQuery);

        if(exam.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var examEntity = exam.get();
        var examResource = ExamResourceFromEntityAssembler.ToResourceFromEntity(examEntity);

        return new ResponseEntity<>(examResource, HttpStatus.CREATED);
    }

    @GetMapping("/{examId}")
    @Operation(summary = "Get exam by id", description = "Get exam by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam found"),
            @ApiResponse(responseCode = "404", description = "Exam not found")
    })
    public ResponseEntity<ExamResource> getExamById(@PathVariable Long examId) {
        var getExamByIdQuery = new GetExamByIdQuery(examId);
        var exam = examQueryService.handle(getExamByIdQuery);
        if (exam.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var examEntity = exam.get();
        var examResource = ExamResourceFromEntityAssembler.ToResourceFromEntity(examEntity);
        return ResponseEntity.ok(examResource);
    }



    @GetMapping
    @Operation(summary = "Get all exams", description = "Get all exams")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exams found"),
            @ApiResponse(responseCode = "404", description = "Exams not found")
    })
    public ResponseEntity<List<ExamResource>> getAllExams() {
        var exams = examQueryService.handle(new GetAllExamsQuery());
        if (exams.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var examResources = exams.stream()
                .map(ExamResourceFromEntityAssembler:: ToResourceFromEntity)
                .toList();
        return ResponseEntity.ok(examResources);
    }
}
