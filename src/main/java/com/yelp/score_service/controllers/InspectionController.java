package com.yelp.score_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yelp.score_service.models.HealthScore;
import com.yelp.score_service.models.Inspection;
import com.yelp.score_service.services.InspectionService;

@RestController
public class InspectionController {
	
	@Autowired
	private InspectionService inspectionService;
	
	@PostMapping("/api/v1/inspection")
	public Inspection createInspection(@RequestBody Inspection inspection) {
		return inspectionService.createInspection(inspection);
	}
	
	@GetMapping("/api/v1/inspection")
	public Iterable<Inspection> getAllInspections() {
		return inspectionService.getAllInspections();
	}
	
	@GetMapping("/api/v1/health-score-by-business/{id}")
	public HealthScore getHealthScoreByBusinessId(@PathVariable int id) {
		return inspectionService.getHealthScoreByBusinessId(id);
	}
	
	@GetMapping("/api/v1/inspections-by-business/{id}")
	public Iterable<Inspection> getInspectionsByBusinessId(@PathVariable int id) {
		return inspectionService.getInspectionsByBusinessId(id);
	}
	
}
