package org.learne.platform.learne.infrastructure.persistence.jpa;

import org.learne.platform.learne.domain.model.aggregates.CoursesEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesEnrollmentRepository extends JpaRepository<CoursesEnrollment, Long> {
    boolean existsByUserIdAndCourseId(Long studentId, Long courseId);
}
