package com.syg.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Company extends MasterEntity{

	@Column(unique = true)
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String mobile;
	
	private String address;
	private String city;
	private String state;
	private String country;
	
}
