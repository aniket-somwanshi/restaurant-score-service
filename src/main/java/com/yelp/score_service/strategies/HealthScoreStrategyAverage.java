package com.yelp.score_service.strategies;

import java.sql.Timestamp;

import com.yelp.score_service.models.HealthScore;
import com.yelp.score_service.models.Inspection;

public class HealthScoreStrategyAverage implements HealthScoreStrategy {

	@Override
	public HealthScore getHealthScore(Iterable<Inspection> inspections) {	
		int totalScore = 0;
        int inspectionCount = 0;

        for (Inspection inspection : inspections) {
            Integer currentScore = inspection.getScore();

            totalScore += currentScore;
            inspectionCount++;
        }

        // Avoid division by zero
        if (inspectionCount == 0) {
            return new HealthScore(0);
        }

        int averageScore = (int) totalScore/inspectionCount;
        
        return new HealthScore(averageScore);
	}
}
