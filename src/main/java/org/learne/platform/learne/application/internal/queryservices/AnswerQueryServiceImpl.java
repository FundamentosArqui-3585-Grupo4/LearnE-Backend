package org.learne.platform.learne.application.internal.queryservices;

import org.learne.platform.learne.domain.model.aggregates.Answer;
import org.learne.platform.learne.domain.model.queries.Answer.GetAllAnswersQuery;
import org.learne.platform.learne.domain.model.queries.Answer.GetAnswerByIdQuery;
import org.learne.platform.learne.domain.services.Answer.AnswerQueryService;
import org.learne.platform.learne.infrastructure.persistence.jpa.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerQueryServiceImpl implements AnswerQueryService {

    private final AnswerRepository answerRepository;

    public AnswerQueryServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Optional<Answer> handle(GetAnswerByIdQuery query) {
        return answerRepository.findById(query.id());
    }

    @Override
    public List<Answer> handle(GetAllAnswersQuery query) {
        return answerRepository.findAll();
    }
}
