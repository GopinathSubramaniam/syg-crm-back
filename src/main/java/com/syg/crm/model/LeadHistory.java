package com.syg.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syg.crm.enums.Operation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.ToString;

@ToString
@Entity
public class LeadHistory extends MasterEntity {

	private String name; // OBJ/ARY/TXT

	@Column(columnDefinition = "text")
	private String value;

	@Column(length = 1)
	private Operation operation;

	@ManyToOne(cascade = CascadeType.ALL)
	private Lead lead;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@JsonIgnore
	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

}
