package com.yelp.score_service.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yelp.score_service.exceptions.ResourceNotFoundException;
import com.yelp.score_service.models.Business;
import com.yelp.score_service.services.BusinessService;

@RestController
public class BusinessController {
	
	@Autowired
	private BusinessService businessService;
	
	@PostMapping("/api/v1/business")
	public Business createBusiness(@RequestBody Business business) {
		return businessService.createBusiness(business);
	}
	
	@GetMapping("/api/v1/business")
	public Iterable<Business> getAllBusinesses() {
		return businessService.getAllBusinesses();
	}
	
	@GetMapping("/api/v1/business/{id}")
	public Business getBusiness(@PathVariable("id") int id) {
		Optional<Business> business = businessService.getBusiness(id);
		if (business.isPresent()) return business.get();
		else throw new ResourceNotFoundException("No Business found with id: " + id);
	}
	
	@PutMapping("/api/v1/business")
	public Business updateBusiness(@RequestBody Business business) {
		return businessService.updateBusiness(business);
	}
	
	@DeleteMapping("/api/v1/business/{id}")
	public void deleteBusiness(@PathVariable("id") int id){
		businessService.deleteBusiness(id);
	}
	
	@GetMapping("/api/v1/outdated-businesses")
	public Iterable<Business> getAllOutdatedBusinesses() {
		return businessService.getAllOutdatedBusinesses();
	}
	
	@GetMapping("/api/v1/is-business-outdated/{id}")
	public boolean isBusinessOutdatedById(@PathVariable("id") int id) {
		return businessService.isBusinessOutdatedById(id);
	}
	
}
