package com.yelp.score_service.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yelp.score_service.models.Business;

@Repository
public interface BusinessRepository extends CrudRepository<Business, Integer> {
	@Modifying
	@Query("UPDATE `score-service`.`business` SET last_scored_at = CURRENT_TIMESTAMP WHERE id = :businessId")
    public void updateLastScoredAt(@Param("businessId") int businessId);

	@Query("SELECT * FROM `score-service`.`business` " +
	           "WHERE last_scored_at < DATE_SUB(CURRENT_TIMESTAMP, INTERVAL :expirationMonths MONTH)")
	List<Business> getAllOutdatedBusinesses(@Param("expirationMonths") int expirationMonths);
}
