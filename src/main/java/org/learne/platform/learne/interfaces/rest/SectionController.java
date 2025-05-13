package org.learne.platform.learne.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.domain.model.queries.Section.GetAllSectionsQuery;
import org.learne.platform.learne.domain.model.queries.Section.GetSectionByIdQuery;
import org.learne.platform.learne.domain.model.queries.Unit.GetAllUnitsQuery;
import org.learne.platform.learne.domain.model.queries.Unit.GetUnitByIdQuery;
import org.learne.platform.learne.domain.services.Section.SectionCommandService;
import org.learne.platform.learne.domain.services.Section.SectionQueryService;
import org.learne.platform.learne.interfaces.rest.resources.Section.CreateSectionResource;
import org.learne.platform.learne.interfaces.rest.resources.Section.SectionResource;
import org.learne.platform.learne.interfaces.rest.resources.Unit.CreateUnitResource;
import org.learne.platform.learne.interfaces.rest.resources.Unit.UnitResource;
import org.learne.platform.learne.interfaces.rest.transform.Section.CreateSectionCommandFromResourceAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Section.SectionResourceFromEntityAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Unit.CreateUnitCommandFromResourceAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Unit.UnitResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/sections", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Sections", description = "Sections API")
public class SectionController {
    private final SectionCommandService sectionCommandService;
    private final SectionQueryService sectionQueryService;
    public SectionController(SectionCommandService sectionCommandService, SectionQueryService sectionQueryService) {
        this.sectionCommandService = sectionCommandService;
        this.sectionQueryService = sectionQueryService;
    }
    @PostMapping
    @Operation(summary = "Create Section", description = "Create section")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Section created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Section not found")
    })
    public ResponseEntity<SectionResource> createSection(@RequestBody CreateSectionResource resource) {
        var createSectionCommand = CreateSectionCommandFromResourceAssembler.ToCommandFromResource(resource);
        var sectionId = sectionCommandService.handle(createSectionCommand);
        if(sectionId == null || sectionId <= 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getSectionByIdQuery = new GetSectionByIdQuery(sectionId);
        var section = sectionQueryService.handle(getSectionByIdQuery);
        if(section.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var sectionEntity = section.get();
        var sectionResource = SectionResourceFromEntityAssembler.ToResourceFromEntity(sectionEntity);
        return new ResponseEntity<>(sectionResource, HttpStatus.CREATED);
    }
    @GetMapping("/{sectionId}")
    @Operation(summary = "Get section by id", description = "Get section by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Section found"),
            @ApiResponse(responseCode = "404", description = "Section not found")
    })
    public ResponseEntity<SectionResource> getSectionById(@PathVariable Long sectionId) {
        var getSectionByIdQuery = new GetSectionByIdQuery(sectionId);
        var section = sectionQueryService.handle(getSectionByIdQuery);
        if (section.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var sectionEntity = section.get();
        var sectionResource = SectionResourceFromEntityAssembler.ToResourceFromEntity(sectionEntity);
        return ResponseEntity.ok(sectionResource);
    }
    @GetMapping
    @Operation(summary = "Get all sections", description = "Get all sections")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sections found"),
            @ApiResponse(responseCode = "404", description = "Sections not found")
    })
    public ResponseEntity<List<SectionResource>> getAllUnits() {
        var sections = sectionQueryService.handle(new GetAllSectionsQuery());
        if (sections.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var sectionResource = sections.stream()
                .map(SectionResourceFromEntityAssembler:: ToResourceFromEntity)
                .toList();
        return ResponseEntity.ok(sectionResource);
    }
}
