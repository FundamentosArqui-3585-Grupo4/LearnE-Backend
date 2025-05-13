package org.learne.platform.learne.application.internal.queryservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Material;
import org.learne.platform.learne.domain.model.queries.Material.GetAllMaterialQuery;
import org.learne.platform.learne.domain.model.queries.Material.GetMaterialByIdQuery;
import org.learne.platform.learne.infrastructure.persistence.jpa.MaterialRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MaterialQueryServiceImplTest {

    private MaterialRepository materialRepository;
    private MaterialQueryServiceImpl materialQueryService;

    @BeforeEach
    void setUp() {
        materialRepository = mock(MaterialRepository.class);
        materialQueryService = new MaterialQueryServiceImpl(materialRepository);
    }

    @Test
    void handle_shouldReturnMaterialById() {
        Material material = mock(Material.class);
        when(materialRepository.findById(1L)).thenReturn(Optional.of(material));

        var result = materialQueryService.handle(new GetMaterialByIdQuery(1L));
        assertTrue(result.isPresent());
    }

    @Test
    void handle_shouldReturnEmptyIfMaterialNotFound() {
        when(materialRepository.findById(1L)).thenReturn(Optional.empty());

        var result = materialQueryService.handle(new GetMaterialByIdQuery(1L));
        assertTrue(result.isEmpty());
    }

    @Test
    void handle_shouldReturnAllMaterials() {
        when(materialRepository.findAll()).thenReturn(Collections.emptyList());

        var result = materialQueryService.handle(new GetAllMaterialQuery());
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}