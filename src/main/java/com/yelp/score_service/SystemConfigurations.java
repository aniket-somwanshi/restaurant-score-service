package com.yelp.score_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yelp.score_service.strategies.HealthScoreStrategy;
import com.yelp.score_service.strategies.HealthScoreStrategyMostRecent;

@Configuration
public class SystemConfigurations {
	@Bean
	public HealthScoreStrategy getHealthScoreStrategy() {
		return new HealthScoreStrategyMostRecent();
	}
}
