package com.yelp.score_service.models;

import org.springframework.data.annotation.Id;

public class Authority {
	@Id
	private int id;
	private String email;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Authority() {}
	
	public Authority(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Authority [id=" + id + ", email=" + email + ", name=" + name + "]";
	}
	
	
}
