package com.yelp.score_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.yelp.score_service.models.Business;
import com.yelp.score_service.repositories.BusinessRepository;

public interface BusinessService {
	
	public Iterable<Business> getAllBusinesses();

	public Business createBusiness(Business business);
	
	public Optional<Business> getBusiness(int id);

	public Business updateBusiness(Business business);
	
	public void deleteBusiness(int id);
	
	public void updateLastScoredAt(int businessId);
	
	public boolean isBusinessOutdatedById(int id);
	
	public Iterable<Business> getAllOutdatedBusinesses();
}
