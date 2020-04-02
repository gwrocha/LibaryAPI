package com.gwrocha.libary.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GeneralConfigurations implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private Boolean startedDataBase;

	public Long getId() {
		return id;
	}

	public GeneralConfigurations() {
		this.startedDataBase = Boolean.FALSE;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getStartedDataBase() {
		return startedDataBase;
	}

	public void setStartedDataBase(Boolean startedDataBase) {
		this.startedDataBase = startedDataBase;
	}

}
