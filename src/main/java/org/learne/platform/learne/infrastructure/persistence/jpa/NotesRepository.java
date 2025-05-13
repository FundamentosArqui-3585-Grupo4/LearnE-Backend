package org.learne.platform.learne.infrastructure.persistence.jpa;


import org.learne.platform.learne.domain.model.aggregates.Notes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
    Optional<Notes> findById(Long id);


}
