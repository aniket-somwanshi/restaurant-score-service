package com.yelp.score_service.models;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;

public class Inspection {
	@Id
	private int id;
	private int businessId;
	private int authorityId;
	private Timestamp createdAt; 
	private int score;
	private String description;
	
	public Inspection() {}
	
	public Inspection(int businessId, int authorityId, Timestamp createdAt, int score, String description) {
		super();
		this.businessId = businessId;
		this.authorityId = authorityId;
		this.createdAt = createdAt;
		this.score = score;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}

	public int getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Inspection [id=" + id + ", businessId=" + businessId + ", authorityId=" + authorityId + ", createdAt="
				+ createdAt + ", score=" + score + ", description=" + description + "]";
	}
	
	
}
