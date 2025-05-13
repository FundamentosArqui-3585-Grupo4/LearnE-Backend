package org.learne.platform.learne.domain.model.commands.CoursesEnrollment;

public record CreateCoursesEnrollmentCommand(Long studentId, Long courseId) {
    public CreateCoursesEnrollmentCommand {
        if (studentId == null || studentId <= 0) {
            throw new IllegalArgumentException("Student Id can't be null or less than 0");
        }
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course Id can't be null or less than 0");
        }
    }
}
