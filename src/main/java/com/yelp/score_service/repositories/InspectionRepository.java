package com.yelp.score_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yelp.score_service.models.Inspection;

@Repository
public interface InspectionRepository extends CrudRepository<Inspection, Integer> {
	List<Inspection> findByBusinessId(int id);

	List<Inspection> findByAuthorityId(int id);
}