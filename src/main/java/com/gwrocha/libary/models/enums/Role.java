package com.gwrocha.libary.models.enums;

public enum Role{

	USER("User"),
	ADMIN("Administrator")
	
	;
	
	private String description;
	
	private Role(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
