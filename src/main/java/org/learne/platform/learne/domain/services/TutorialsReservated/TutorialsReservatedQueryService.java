package org.learne.platform.learne.domain.services.TutorialsReservated;

import org.learne.platform.learne.domain.model.aggregates.TutorialsReservated;
import org.learne.platform.learne.domain.model.queries.TutorialsReservated.GetAllTutorialsReservatedQuery;

import java.util.List;

public interface TutorialsReservatedQueryService {
    List<TutorialsReservated> handle(GetAllTutorialsReservatedQuery query);
}
