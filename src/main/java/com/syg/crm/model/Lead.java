package com.syg.crm.model;

import com.syg.crm.enums.LeadStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Lead extends MasterEntity {

	@Column(length = 50)
	private String leadSource;

	@Column(length = 100)
	private String firstName;

	@Column(length = 100)
	private String lastName;

	@Column(length = 100)
	private String email;

	@Column(length = 15)
	private String mobile;
	private String address;

	@Column(length = 40)
	private String city;

	@Column(length = 40)
	private String state;

	@Column(length = 40)
	private String country;

	@Column(length = 20)
	private String zip;

	private String description;
	private String tags;

	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private LeadStatus status = LeadStatus.N;

	@ManyToOne
	private UserDetail userDetail; // Lead Owner

	@ManyToOne
	private UserDetail createdUserDetail;

}
