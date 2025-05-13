package org.learne.platform.learne.interfaces.rest.resources.Material;

public record MaterialResource(Long id,Long course_id,
                               String title,String format,String link) {
}
