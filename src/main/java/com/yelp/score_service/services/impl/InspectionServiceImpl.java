package com.yelp.score_service.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yelp.score_service.models.HealthScore;
import com.yelp.score_service.models.Inspection;
import com.yelp.score_service.repositories.InspectionRepository;
import com.yelp.score_service.services.BusinessService;
import com.yelp.score_service.services.InspectionService;
import com.yelp.score_service.strategies.HealthScoreStrategy;

@Service
public class InspectionServiceImpl implements InspectionService {
	
	@Autowired
	private InspectionRepository inspectionRepository;
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private HealthScoreStrategy healthScoreStrategy;
	
	@Override
	public Inspection createInspection(Inspection inspection) {
		// create inspection
		Inspection savedInspection = inspectionRepository.save(inspection);
		
		// update the last_scored_at date for the business
		businessService.updateLastScoredAt(inspection.getBusinessId());
		
		return savedInspection;
	}

	@Override
	public HealthScore getHealthScoreByBusinessId(int id) {
		List<Inspection> inspections = inspectionRepository.findByBusinessId(id);
		return healthScoreStrategy.getHealthScore(inspections);
	}


	@Override
	public Iterable<Inspection> getInspectionsByBusinessId(int id) {
		return inspectionRepository.findByBusinessId(id);
	}

	@Override
	public Iterable<Inspection> getAllInspections() {
		return inspectionRepository.findAll();
	}

	@Override
	public Iterable<Inspection> getInspectionsByAuthorityId(int id) {
		return inspectionRepository.findByAuthorityId(id);
	}
	
	
}
