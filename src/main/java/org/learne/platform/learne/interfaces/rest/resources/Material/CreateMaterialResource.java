package org.learne.platform.learne.interfaces.rest.resources.Material;

import jakarta.persistence.Column;

public record CreateMaterialResource( Long course_id,
                                     String title,String format,String link) {
}
