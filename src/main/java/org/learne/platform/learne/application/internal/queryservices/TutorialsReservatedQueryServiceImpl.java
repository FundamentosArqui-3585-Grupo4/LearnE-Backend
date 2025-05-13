package org.learne.platform.learne.application.internal.queryservices;

import org.learne.platform.learne.domain.model.aggregates.TutorialsReservated;
import org.learne.platform.learne.domain.model.queries.TutorialsReservated.GetAllTutorialsReservatedQuery;
import org.learne.platform.learne.domain.services.TutorialsReservated.TutorialsReservatedQueryService;
import org.learne.platform.learne.infrastructure.persistence.jpa.TutorialsReservatedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialsReservatedQueryServiceImpl implements TutorialsReservatedQueryService {
    private final TutorialsReservatedRepository tutorialsReservatedRepository;
    public TutorialsReservatedQueryServiceImpl(TutorialsReservatedRepository tutorialsReservatedRepository) {
        this.tutorialsReservatedRepository = tutorialsReservatedRepository;
    }

    @Override
    public List<TutorialsReservated> handle(GetAllTutorialsReservatedQuery query) {
        return tutorialsReservatedRepository.findAll();
    }
}
