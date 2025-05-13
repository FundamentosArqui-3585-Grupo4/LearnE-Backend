package org.learne.platform.learne.domain.services.Section;

import org.learne.platform.learne.domain.model.commands.Section.CreateSectionCommand;

public interface SectionCommandService {
    Long handle(CreateSectionCommand command);
}
