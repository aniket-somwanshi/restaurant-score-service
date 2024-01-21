package com.yelp.score_service.services;

import com.yelp.score_service.models.Business;
import com.yelp.score_service.models.HealthScore;
import com.yelp.score_service.models.Inspection;

public interface InspectionService {
	public Inspection createInspection(Inspection inspection);
	
	public HealthScore getHealthScoreByBusinessId(int id);
	
	public Iterable<Inspection> getInspectionsByBusinessId(int id);

	public Iterable<Inspection> getAllInspections();

	public Iterable<Inspection> getInspectionsByAuthorityId(int id);	
}

