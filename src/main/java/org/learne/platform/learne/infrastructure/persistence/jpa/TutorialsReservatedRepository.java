package org.learne.platform.learne.infrastructure.persistence.jpa;

import org.learne.platform.learne.domain.model.aggregates.TutorialsReservated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorialsReservatedRepository extends JpaRepository<TutorialsReservated,Long> {
    boolean existsByUserIdAndTutorialsCoursesId(Long studentId, Long tutorialsId);
}
