package com.yelp.score_service.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yelp.score_service.models.Authority;
import com.yelp.score_service.repositories.AuthorityRepository;
import com.yelp.score_service.services.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public Iterable<Authority> getAllAuthorities() {
		return authorityRepository.findAll(); 
	}

	public Authority createAuthority(Authority authority) {
		return authorityRepository.save(authority);
	}
	
	public Optional<Authority> getAuthority(int id) {
		return authorityRepository.findById(id);
	}

	public Authority updateAuthority(Authority authority) {
		return authorityRepository.save(authority);
	}
	
	public void deleteAuthority(int id) {
		authorityRepository.deleteById(id);
	}
	
}
