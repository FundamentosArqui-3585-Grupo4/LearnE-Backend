package org.learne.platform.learne.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.learne.platform.learne.domain.model.queries.CoursesEnrollment.GetAllCoursesEnrollmentQuery;
import org.learne.platform.learne.domain.services.CoursesEnrollment.CoursesEnrollmentCommandService;
import org.learne.platform.learne.domain.services.CoursesEnrollment.CoursesEnrollmentQueryService;
import org.learne.platform.learne.interfaces.rest.resources.CoursesEnrollment.CoursesEnrollmentResource;
import org.learne.platform.learne.interfaces.rest.resources.CoursesEnrollment.CreateCoursesEnrollmentResource;
import org.learne.platform.learne.interfaces.rest.transform.CoursesEnrollment.CoursesEnrollmentResourceFromEntityAssembler;
import org.learne.platform.learne.interfaces.rest.transform.CoursesEnrollment.CreateCoursesEnrollmentCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/courses_enrollment", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Courses Enrollment", description = "Courses enrollment API")
public class CoursesEnrollmentController {
    private final CoursesEnrollmentCommandService coursesEnrollmentCommandService;
    private final CoursesEnrollmentQueryService coursesEnrollmentQueryService;
    public CoursesEnrollmentController(CoursesEnrollmentCommandService coursesEnrollmentCommandService, CoursesEnrollmentQueryService coursesEnrollmentQueryService) {
        this.coursesEnrollmentCommandService = coursesEnrollmentCommandService;
        this.coursesEnrollmentQueryService = coursesEnrollmentQueryService;
    }
    @GetMapping
    @Operation(
            summary = "Search for all courses enrollment",
            description = "Searches for all created courses enrollment on the platform"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Courses enrollment found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    private ResponseEntity<List<CoursesEnrollmentResource>> getAllCourses() {
        var getAllCoursesEnrollmentQuery = new GetAllCoursesEnrollmentQuery();
        var coursesEnrollments = coursesEnrollmentQueryService.handle(getAllCoursesEnrollmentQuery);
        var coursesEnrollmentsResources = coursesEnrollments.stream().map(CoursesEnrollmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(coursesEnrollmentsResources);
    }
    @Operation(
            summary = "Create a course enrollment",
            description = "Create a course enrollment using the parameters provided by the endpoint"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @PostMapping
    public ResponseEntity<CoursesEnrollmentResource> createCourse(@RequestBody CreateCoursesEnrollmentResource resource){
        Optional<CoursesEnrollment> coursesEnrollment = coursesEnrollmentCommandService
                .handle(CreateCoursesEnrollmentCommandFromResourceAssembler.toCommandFromResource(resource));
        return coursesEnrollment.map(source -> new ResponseEntity<>(CoursesEnrollmentResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
