package org.learne.platform.learne.infrastructure.persistence.jpa;

import org.learne.platform.learne.domain.model.aggregates.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findById(Long id);

    boolean existsByQuestionIdAndDescription(Long questionId, String description);

    boolean existsByQuestionIdAndIsCorrect(Long questionId, Boolean isCorrect);
}
