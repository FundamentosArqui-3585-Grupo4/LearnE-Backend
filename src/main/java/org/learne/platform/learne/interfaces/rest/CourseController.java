package org.learne.platform.learne.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.domain.model.queries.GetAllCoursesQuery;
import org.learne.platform.learne.domain.model.queries.GetCourseByIdQuery;
import org.learne.platform.learne.domain.services.Course.CourseCommandService;
import org.learne.platform.learne.domain.services.Course.CourseQueryService;
import org.learne.platform.learne.interfaces.rest.resources.Course.CourseResource;
import org.learne.platform.learne.interfaces.rest.resources.Course.CreateCourseResource;
import org.learne.platform.learne.interfaces.rest.transform.Course.CourseResourceFromEntityAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Course.CreateCourseCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value="/api/v1/courses", produces = APPLICATION_JSON_VALUE)
@Tag(name="Courses", description = "Various operations for managing courses towards the user..")
public class CourseController {
    private final CourseQueryService courseQueryService;
    private final CourseCommandService courseCommandService;

    public CourseController(CourseQueryService courseQueryService, CourseCommandService courseCommandService) {
        this.courseQueryService = courseQueryService;
        this.courseCommandService = courseCommandService;
    }

    @Operation(
            summary = "Search for all courses",
            description = "Searches for all created courses on the platform"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Courses found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @GetMapping
    private ResponseEntity<List<CourseResource>> getAllCourses() {
        var getAllCourses = new GetAllCoursesQuery();
        var courses = courseQueryService.handle(getAllCourses);
        var coursesResources = courses.stream().map(CourseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(coursesResources);
    }

    @Operation(
            summary = "Search for a specific course",
            description = "Searches for a specific course using their title"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<CourseResource> getCourseById(@PathVariable Long id) {
        var getCourseByIdQuery = new GetCourseByIdQuery(id);
        var course = courseQueryService.handle(getCourseByIdQuery);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var courseEntity = course.get();
        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(courseEntity);
        return ResponseEntity.ok(courseResource);
    }

    @Operation(
            summary = "Create a course",
            description = "Create a course using the parameters provided by the endpoint"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @PostMapping
    public ResponseEntity<CourseResource> createCourse(@RequestBody CreateCourseResource resource){
        Optional<Course> course = courseCommandService
                .handle(CreateCourseCommandFromResourceAssembler.toCommand(resource));
        return course.map(source -> new ResponseEntity<>(CourseResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
