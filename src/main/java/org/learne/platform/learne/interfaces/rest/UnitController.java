package org.learne.platform.learne.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.domain.model.queries.Exam.GetAllExamsQuery;
import org.learne.platform.learne.domain.model.queries.Exam.GetExamByIdQuery;
import org.learne.platform.learne.domain.model.queries.Unit.GetAllUnitsQuery;
import org.learne.platform.learne.domain.model.queries.Unit.GetUnitByIdQuery;
import org.learne.platform.learne.domain.services.Unit.UnitCommandService;
import org.learne.platform.learne.domain.services.Unit.UnitQueryService;
import org.learne.platform.learne.interfaces.rest.resources.Exam.CreateExamResource;
import org.learne.platform.learne.interfaces.rest.resources.Exam.ExamResource;
import org.learne.platform.learne.interfaces.rest.resources.Unit.CreateUnitResource;
import org.learne.platform.learne.interfaces.rest.resources.Unit.UnitResource;
import org.learne.platform.learne.interfaces.rest.transform.Exam.CreateExamCommandFromResourceAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Exam.ExamResourceFromEntityAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Unit.CreateUnitCommandFromResourceAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Unit.UnitResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/units", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Units", description = "Units API")
public class UnitController {
    private final UnitCommandService unitCommandService;
    private final UnitQueryService unitQueryService;
    public UnitController(UnitCommandService unitCommandService,UnitQueryService unitQueryService) {
        this.unitCommandService = unitCommandService;
        this.unitQueryService = unitQueryService;
    }
    @PostMapping
    @Operation(summary = "Create Unit", description = "Create unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unit created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Unit not found")
    })
    public ResponseEntity<UnitResource> createUnit(@RequestBody CreateUnitResource resource) {
        var createUnitCommand = CreateUnitCommandFromResourceAssembler.ToCommandFromResource(resource);
        var unitId = unitCommandService.handle(createUnitCommand);
        if(unitId == null || unitId <= 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getUnitByIdQuery = new GetUnitByIdQuery(unitId);
        var unit = unitQueryService.handle(getUnitByIdQuery);
        if(unit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var unitEntity = unit.get();
        var unitResource = UnitResourceFromEntityAssembler.ToResourceFromEntity(unitEntity);
        return new ResponseEntity<>(unitResource, HttpStatus.CREATED);
    }
    @GetMapping("/{unitId}")
    @Operation(summary = "Get unit by id", description = "Get unit by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unit found"),
            @ApiResponse(responseCode = "404", description = "Unit not found")
    })
    public ResponseEntity<UnitResource> getUnitById(@PathVariable Long unitId) {
        var getUnitByIdQuery = new GetUnitByIdQuery(unitId);
        var unit = unitQueryService.handle(getUnitByIdQuery);
        if (unit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var unitEntity = unit.get();
        var unitResource = UnitResourceFromEntityAssembler.ToResourceFromEntity(unitEntity);
        return ResponseEntity.ok(unitResource);
    }
    @GetMapping
    @Operation(summary = "Get all units", description = "Get all units")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Units found"),
            @ApiResponse(responseCode = "404", description = "Units not found")
    })
    public ResponseEntity<List<UnitResource>> getAllUnits() {
        var units = unitQueryService.handle(new GetAllUnitsQuery());
        if (units.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var unitResource = units.stream()
                .map(UnitResourceFromEntityAssembler:: ToResourceFromEntity)
                .toList();
        return ResponseEntity.ok(unitResource);
    }
}