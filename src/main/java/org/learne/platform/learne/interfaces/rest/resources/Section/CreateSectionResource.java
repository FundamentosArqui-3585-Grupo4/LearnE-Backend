package org.learne.platform.learne.interfaces.rest.resources.Section;

public record CreateSectionResource(Long unit_id, String title, String description, String url_video) {
}
