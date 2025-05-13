package org.learne.platform.learne.infrastructure.persistence.jpa;

import org.learne.platform.learne.domain.model.aggregates.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    Optional<Material> findById(Long id);
    boolean existsById(Long id);
    boolean existsByCourseIdAndTitle(Long id, String title);
}
