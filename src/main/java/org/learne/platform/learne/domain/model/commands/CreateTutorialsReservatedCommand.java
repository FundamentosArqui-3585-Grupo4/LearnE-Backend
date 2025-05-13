package org.learne.platform.learne.domain.model.commands;

public record CreateTutorialsReservatedCommand(Long studentId, Long tutorialId) {
    public CreateTutorialsReservatedCommand {
        if (studentId == null || studentId <= 0) {
            throw new IllegalArgumentException("Student Id must not be null or less than zero");
        }
        if (tutorialId == null || tutorialId <= 0) {
            throw new IllegalArgumentException("Tutorial Id must not be null or less than zero");
        }
    }
}
