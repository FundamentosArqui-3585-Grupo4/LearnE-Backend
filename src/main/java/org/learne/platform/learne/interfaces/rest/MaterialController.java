package org.learne.platform.learne.interfaces.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.learne.platform.learne.domain.model.commands.Material.DeleteMaterialCommand;
import org.learne.platform.learne.domain.model.queries.Exam.GetAllExamsQuery;
import org.learne.platform.learne.domain.model.queries.Exam.GetExamByIdQuery;
import org.learne.platform.learne.domain.model.queries.Material.GetAllMaterialQuery;
import org.learne.platform.learne.domain.model.queries.Material.GetMaterialByIdQuery;
import org.learne.platform.learne.domain.services.Material.MaterialCommandService;
import org.learne.platform.learne.domain.services.Material.MaterialQueryService;
import org.learne.platform.learne.infrastructure.persistence.jpa.MaterialRepository;
import org.learne.platform.learne.interfaces.rest.resources.Exam.CreateExamResource;
import org.learne.platform.learne.interfaces.rest.resources.Exam.ExamResource;
import org.learne.platform.learne.interfaces.rest.resources.Material.CreateMaterialResource;
import org.learne.platform.learne.interfaces.rest.resources.Material.MaterialResource;
import org.learne.platform.learne.interfaces.rest.transform.Exam.CreateExamCommandFromResourceAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Exam.ExamResourceFromEntityAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Material.CreateMaterialCommandFromResourceAssembler;
import org.learne.platform.learne.interfaces.rest.transform.Material.MaterialResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/materials", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Materials", description = "Materials API")

public class MaterialController {

    private final MaterialCommandService materialCommandService;
    private final MaterialQueryService materialQueryService;

    public MaterialController(MaterialCommandService materialCommandService, MaterialQueryService materialQueryService) {
        this.materialCommandService = materialCommandService;
        this.materialQueryService = materialQueryService;
    }
    @PostMapping
    @Operation(summary = "Create material", description = "Create material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Material not found")
    })
    public ResponseEntity<MaterialResource> createMaterial(@RequestBody CreateMaterialResource resource) {
        var createMaterialCommand = CreateMaterialCommandFromResourceAssembler.ToCommandFromResource(resource);
        var materialId = materialCommandService.handle(createMaterialCommand);

        if(materialId == null || materialId <= 0L) {
            return ResponseEntity.badRequest().build();
        }

        var getMaterialByIdQuery = new GetMaterialByIdQuery(materialId);
        var material = materialQueryService.handle(getMaterialByIdQuery);

        if(material.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var materialEntity = material.get();
        var materialResource = MaterialResourceFromEntityAssembler.ToResourceFromEntity(materialEntity);

        return new ResponseEntity<>(materialResource, HttpStatus.CREATED);
    }

    @GetMapping("/{materialId}")
    @Operation(summary = "Get material by id", description = "Get material by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material found"),
            @ApiResponse(responseCode = "404", description = "Material not found")
    })

    public ResponseEntity<MaterialResource> getMaterialById(@PathVariable Long materialId) {
        var getMaterialByIdQuery = new GetMaterialByIdQuery(materialId);
        var material = materialQueryService.handle(getMaterialByIdQuery);
        if (material.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var materialEntity = material.get();
        var materialResource = MaterialResourceFromEntityAssembler.ToResourceFromEntity(materialEntity);
        return ResponseEntity.ok(materialResource);
    }

    @GetMapping
    @Operation(summary = "Get all materials", description = "Get all materials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material found"),
            @ApiResponse(responseCode = "404", description = "Material not found")
    })
    public ResponseEntity<List<MaterialResource>> getAllMaterial() {
        var materials = materialQueryService.handle(new GetAllMaterialQuery());
        if (materials.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var materialResources = materials.stream()
                .map(MaterialResourceFromEntityAssembler:: ToResourceFromEntity)
                .toList();
        return ResponseEntity.ok(materialResources);
    }
    @DeleteMapping("/{materialId}")
    @Operation(
            summary = "Delete a Material",
            description = "Deletes a Material using the material ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Material Deleted Successfully "),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    public ResponseEntity<?> deleteMaterial(@PathVariable Long materialId) {
        var deleteMaterialCommand = new DeleteMaterialCommand(materialId);
        materialCommandService.handle(deleteMaterialCommand);
        return ResponseEntity.noContent().build();
    }
}
