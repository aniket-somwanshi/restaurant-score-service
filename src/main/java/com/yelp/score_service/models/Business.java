package com.yelp.score_service.models;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;

public class Business {
	@Id
	private int id;
	private String name;
	private String address;
	private String city;
	private String state;
	private int postalCode;
	private String phoneNumber;
	
	private Timestamp lastScoredAt;
	
	public Business() {}

	public Business(String name, String address, String city, String state, int postalCode, int latitude,
			int longitude, String phoneNumber, Timestamp lastScoredAt) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.lastScoredAt = lastScoredAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getpostalCode() {
		return postalCode;
	}

	public void setpostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getphoneNumber() {
		return phoneNumber;
	}

	public void setphoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Timestamp getlastScoredAt() {
		return lastScoredAt;
	}

	public void setlastScoredAt(Timestamp lastScoredAt) {
		this.lastScoredAt = lastScoredAt;
	}

	@Override
	public String toString() {
		return "Business [id=" + id + ", name=" + name + ", address=" + address + ", city=" + city + ", state=" + state
				+ ", postalCode=" + postalCode + ", phoneNumber=" + phoneNumber + ", lastScoredAt=" + lastScoredAt + "]";
	}
	
	
	
	
}
