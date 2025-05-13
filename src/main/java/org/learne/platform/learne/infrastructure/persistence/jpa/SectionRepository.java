package org.learne.platform.learne.infrastructure.persistence.jpa;

import org.learne.platform.learne.domain.model.aggregates.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findById(Long id);
    boolean existsByTitle(String title);
}
