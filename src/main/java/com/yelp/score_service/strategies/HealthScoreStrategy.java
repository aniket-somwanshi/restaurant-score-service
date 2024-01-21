package com.yelp.score_service.strategies;

import com.yelp.score_service.models.HealthScore;
import com.yelp.score_service.models.Inspection;

public interface HealthScoreStrategy {
	public HealthScore getHealthScore(Iterable<Inspection> inspections);
}
