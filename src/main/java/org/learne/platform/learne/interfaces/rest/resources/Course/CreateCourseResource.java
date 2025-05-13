package org.learne.platform.learne.interfaces.rest.resources.Course;

public record CreateCourseResource(String title, String description,
                                   Long teacher_id, String level, String duration,
                                   String prior_knowledge, String principal_image, String url_video) {
}
