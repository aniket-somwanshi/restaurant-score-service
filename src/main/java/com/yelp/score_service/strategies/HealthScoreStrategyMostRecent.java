package com.yelp.score_service.strategies;

import java.sql.Timestamp;

import com.yelp.score_service.models.HealthScore;
import com.yelp.score_service.models.Inspection;


public class HealthScoreStrategyMostRecent implements HealthScoreStrategy {

	@Override
	public HealthScore getHealthScore(Iterable<Inspection> inspections) {		
		Inspection latestInspection = null;
        Timestamp latestTimestamp = null;

        for (Inspection inspection : inspections) {
            Timestamp currentTimestamp = inspection.getCreatedAt();

            if (latestTimestamp == null || currentTimestamp.after(latestTimestamp)) {
                latestTimestamp = currentTimestamp;
                latestInspection = inspection;
            }
        }
        
        if (latestInspection == null) {
        	return new HealthScore(0);
        }
        
        return new HealthScore(latestInspection.getScore());
	}
}
