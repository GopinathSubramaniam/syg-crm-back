package com.syg.crm.model;

import com.syg.crm.enums.WorkPosition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserDetail extends MasterEntity {

	@Column(unique = true, length = 10)
	private String userCode;

	@Column(length = 150)
	private String firstName;

	@Column(length = 150)
	private String lastName;

	@Column(length = 15)
	private String mobile;
	
	@Column(unique = true, length = 50)
	private String email;

	@Column(length = 2)
	@Enumerated(EnumType.STRING)
	private WorkPosition workPosition;
	
	private String address;

	@Column(length = 50)
	private String city;

	@Column(length = 50)
	private String state;

	@Column(length = 50)
	private String country;

	@ManyToOne
	private Branch branch;

}
