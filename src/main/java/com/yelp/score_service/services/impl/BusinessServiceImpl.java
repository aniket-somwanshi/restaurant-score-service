package com.yelp.score_service.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yelp.score_service.models.Business;
import com.yelp.score_service.repositories.BusinessRepository;
import com.yelp.score_service.services.BusinessService;

@Service
public class BusinessServiceImpl implements BusinessService {

	
	@Autowired
	private BusinessRepository businessRepository;
	
	@Value("${app.business-status-strategy.expiration-months}")
	private int expirationMonths;
	
	public Iterable<Business> getAllBusinesses() {
		return businessRepository.findAll(); 
	}

	public Business createBusiness(Business business) {
		return businessRepository.save(business);
	}
	
	public Optional<Business> getBusiness(int id) {
		return businessRepository.findById(id);
	}

	public Business updateBusiness(Business business) {
		return businessRepository.save(business);
	}
	
	public void deleteBusiness(int id) {
		businessRepository.deleteById(id);
	}

	@Override
	public void updateLastScoredAt(int business_id) {
		businessRepository.updateLastScoredAt(business_id);
	}

	@Override
	public boolean isBusinessOutdatedById(int id) {
		Business business = businessRepository.findById(id).get();
		LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime thresholdTime = currentTime.minusMonths(expirationMonths);
        return business.getlastScoredAt().toLocalDateTime().isBefore(thresholdTime);
	}

	@Override
	public Iterable<Business> getAllOutdatedBusinesses() {
		return businessRepository.getAllOutdatedBusinesses(expirationMonths);
	}
}
