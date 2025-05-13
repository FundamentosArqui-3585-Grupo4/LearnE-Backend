package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Unit;
import org.learne.platform.learne.domain.model.commands.Unit.CreateUnitCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.UnitRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UnitCommandServiceImplTest {

    private UnitRepository unitRepository;
    private UnitCommandServiceImpl unitCommandService;

    @BeforeEach
    void setUp() {
        unitRepository = mock(UnitRepository.class);
        unitCommandService = new UnitCommandServiceImpl(unitRepository);
    }

    @Test
    void handle_ShouldCreateUnitSuccessfully() {
        CreateUnitCommand command = new CreateUnitCommand(1L, "Unit A");
        when(unitRepository.save(any(Unit.class))).thenAnswer(i -> {
            Unit u = i.getArgument(0);
            u.setId(10L);
            return u;
        });

        Long id = unitCommandService.handle(command);

        assertThat(id).isEqualTo(10L);
        verify(unitRepository).save(any(Unit.class));
    }
}