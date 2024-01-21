package com.yelp.score_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yelp.score_service.models.Authority;
import com.yelp.score_service.models.Business;
import com.yelp.score_service.services.AuthorityService;

@RestController
public class AuthorityController {
	
	@Autowired
	private AuthorityService authorityService;
	
	@PostMapping("/api/v1/authority")
	public Authority createAuthority(@RequestBody Authority authority) {
		return authorityService.createAuthority(authority);
	}
	
	@GetMapping("/api/v1/authority")
	public Iterable<Authority> getAllAuthorities() {
		return authorityService.getAllAuthorities();
	}
	
	@GetMapping("/api/v1/authority/{id}")
	public Authority getAuthority(@PathVariable("id") int id) {
		return authorityService.getAuthority(id).get();
	}
	
	@PutMapping("/api/v1/authority")
	public Authority updateAuthority(@RequestBody Authority authority) {
		return authorityService.updateAuthority(authority);
	}
	
	@DeleteMapping("/api/v1/authority/{id}")
	public void deleteAuthority(@PathVariable("id") int id){
		authorityService.deleteAuthority(id);
	}
	
}
