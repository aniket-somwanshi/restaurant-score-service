package com.yelp.score_service.models;

public class HealthScore {
	private int score;
	
	public HealthScore() {}
	
	public HealthScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "HealthScore [score=" + score + "]";
	}
	
	
}
