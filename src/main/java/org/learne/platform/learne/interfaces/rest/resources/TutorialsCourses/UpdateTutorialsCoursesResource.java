package org.learne.platform.learne.interfaces.rest.resources.TutorialsCourses;

public record UpdateTutorialsCoursesResource(Long courses_id, Long teacher_id, String date, String hour,
                                             Boolean is_reservated, String link) {
    public UpdateTutorialsCoursesResource {
        if (courses_id == null || courses_id <= 0L) {
            throw new IllegalArgumentException("courseId cannot be null");
        }
        if (teacher_id == null || teacher_id <= 0L) {
            throw new IllegalArgumentException("teacherId cannot be null");
        }
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("date cannot be blank");
        }
        if (hour == null || hour.isBlank()) {
            throw new IllegalArgumentException("hour cannot be blank");
        }
        if (is_reservated == null) {
            throw new IllegalArgumentException("isReservated cannot be null");
        }
        if (link == null || link.isBlank()) {
            throw new IllegalArgumentException("link cannot be blank");
        }
    }
}
