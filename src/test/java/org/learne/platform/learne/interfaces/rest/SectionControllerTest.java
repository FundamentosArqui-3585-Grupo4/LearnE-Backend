package org.learne.platform.learne.interfaces.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Section;
import org.learne.platform.learne.domain.model.aggregates.Unit;
import org.learne.platform.learne.domain.model.commands.Section.CreateSectionCommand;
import org.learne.platform.learne.domain.model.queries.Section.GetAllSectionsQuery;
import org.learne.platform.learne.domain.model.queries.Section.GetSectionByIdQuery;
import org.learne.platform.learne.domain.services.Section.SectionCommandService;
import org.learne.platform.learne.domain.services.Section.SectionQueryService;
import org.learne.platform.learne.interfaces.rest.resources.Section.CreateSectionResource;
import org.learne.platform.learne.interfaces.rest.resources.Section.SectionResource;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SectionControllerTest {

    private SectionCommandService sectionCommandService;
    private SectionQueryService sectionQueryService;
    private SectionController sectionController;

    @BeforeEach
    void setUp() {
        sectionCommandService = mock(SectionCommandService.class);
        sectionQueryService = mock(SectionQueryService.class);
        sectionController = new SectionController(sectionCommandService, sectionQueryService);
    }

    @Test
    void createSection_shouldReturn201() {
        CreateSectionResource resource = new CreateSectionResource(1L, "Título", "Descripción", "video.com");
        CreateSectionCommand command = new CreateSectionCommand(1L, "Título", "Descripción", "video.com");

        Section section = new Section(command);
        section.setId(11L);

        when(sectionCommandService.handle(any())).thenReturn(11L);
        when(sectionQueryService.handle(any(GetSectionByIdQuery.class))).thenReturn(Optional.of(section));

        ResponseEntity<SectionResource> response = sectionController.createSection(resource);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getSectionById_shouldReturn200() {
        Unit unit = new Unit();
        unit.setId(1L);

        Section section = new Section();
        section.setId(1L);
        section.setUnit(unit); // 👈 obligatorio para evitar NPE

        when(sectionQueryService.handle(any(GetSectionByIdQuery.class))).thenReturn(Optional.of(section));

        ResponseEntity<SectionResource> response = sectionController.getSectionById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getAllSections_shouldReturn200() {
        Unit unit = new Unit();
        unit.setId(1L);

        Section s1 = new Section();
        s1.setId(1L);
        s1.setUnit(unit);

        Section s2 = new Section();
        s2.setId(2L);
        s2.setUnit(unit);

        when(sectionQueryService.handle(any(GetAllSectionsQuery.class)))
                .thenReturn(List.of(s1, s2));

        ResponseEntity<List<SectionResource>> response = sectionController.getAllUnits();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }
}