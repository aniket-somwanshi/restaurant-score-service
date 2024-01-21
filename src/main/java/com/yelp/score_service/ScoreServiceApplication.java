package com.yelp.score_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.yelp")
public class ScoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreServiceApplication.class, args);
	}

}
