package org.learne.platform.learne.application.internal.queryservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Section;
import org.learne.platform.learne.domain.model.queries.Section.GetAllSectionsQuery;
import org.learne.platform.learne.domain.model.queries.Section.GetSectionByIdQuery;
import org.learne.platform.learne.infrastructure.persistence.jpa.SectionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SectionQueryServiceImplTest {

    private SectionRepository sectionRepository;
    private SectionQueryServiceImpl sectionQueryService;

    @BeforeEach
    void setUp() {
        sectionRepository = mock(SectionRepository.class);
        sectionQueryService = new SectionQueryServiceImpl(sectionRepository);
    }

    @Test
    void handle_shouldReturnSectionById() {
        Section section = new Section();
        section.setId(1L);
        when(sectionRepository.findById(1L)).thenReturn(Optional.of(section));

        var result = sectionQueryService.handle(new GetSectionByIdQuery(1L));
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void handle_shouldReturnAllSections() {
        when(sectionRepository.findAll()).thenReturn(List.of(new Section(), new Section()));
        var result = sectionQueryService.handle(new GetAllSectionsQuery());
        assertEquals(2, result.size());
    }
}