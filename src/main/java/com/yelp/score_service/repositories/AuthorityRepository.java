package com.yelp.score_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yelp.score_service.models.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

}
