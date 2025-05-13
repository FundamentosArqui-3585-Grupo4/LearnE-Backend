package org.learne.platform.learne.domain.model.commands.TutorialsCourses;

public record UpdateTutorialsCoursesCommand(Long tutorialId, Long courseId, Long teacherId, String date,
                                            String hour, Boolean isReservated, String link) {
    public UpdateTutorialsCoursesCommand {
        if (tutorialId == null || tutorialId <= 0) {
            throw new IllegalArgumentException("Tutorial Id is required");
        }
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course Id is required");
        }
        if (teacherId == null || teacherId <= 0) {
            throw new IllegalArgumentException("Teacher Id is required");
        }
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Date is required");
        }
        if (hour == null || hour.isBlank()) {
            throw new IllegalArgumentException("Hour is required");
        }
        if (isReservated == null || !isReservated) {
            throw new IllegalArgumentException("Is Reservated is false");
        }
    }
}
