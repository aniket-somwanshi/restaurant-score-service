package com.yelp.score_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.yelp.score_service.models.Authority;
import com.yelp.score_service.models.Business;
import com.yelp.score_service.repositories.BusinessRepository;

public interface AuthorityService {
	
	public Iterable<Authority> getAllAuthorities(); 

	public Authority createAuthority(Authority authority);
	
	public Optional<Authority> getAuthority(int id);

	public Authority updateAuthority(Authority authority);
	
	public void deleteAuthority(int id);
}
