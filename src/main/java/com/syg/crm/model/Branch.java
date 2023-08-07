package com.syg.crm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Branch extends MasterEntity {
	
	private String code;
	private String name;
	private String email;
	private String mobile;
	private String city;
	private String state;
	private String country;
	private String contactPerson;

	@ManyToOne
	private Company company;
	

}
