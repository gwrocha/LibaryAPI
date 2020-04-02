package com.gwrocha.libary.config.security.dto;

public class TokenResponse {

	private String type;
	
	private String token;

	public TokenResponse() {
		this.type = "Baerer";
	}
	
	public TokenResponse(String token) {
		this();
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
