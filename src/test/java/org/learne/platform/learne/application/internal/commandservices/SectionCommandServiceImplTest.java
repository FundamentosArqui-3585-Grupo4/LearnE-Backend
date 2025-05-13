package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Section;
import org.learne.platform.learne.domain.model.commands.Section.CreateSectionCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.SectionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SectionCommandServiceImplTest {

    private SectionRepository sectionRepository;
    private SectionCommandServiceImpl sectionCommandService;

    @BeforeEach
    void setUp() {
        sectionRepository = mock(SectionRepository.class);
        sectionCommandService = new SectionCommandServiceImpl(sectionRepository);
    }

    @Test
    void handle_shouldCreateSectionSuccessfully() {
        // Arrange
        CreateSectionCommand command = new CreateSectionCommand(1L, "Intro", "Descripción", "http://video.com");

        when(sectionRepository.existsByTitle("Intro")).thenReturn(false);
        when(sectionRepository.save(any(Section.class))).thenAnswer(invocation -> {
            Section s = invocation.getArgument(0);
            s.setId(99L);
            return s;
        });

        // Act
        Long sectionId = sectionCommandService.handle(command);

        // Assert
        assertNotNull(sectionId);
        assertEquals(99L, sectionId);
    }
}