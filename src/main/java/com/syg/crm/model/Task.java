package com.syg.crm.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Task extends MasterEntity {

	private String taskNum;
	
	@Column(length = 100)
	private String subject;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	/**
	 * 0 - Low, 1 - Medium, 2 - High, 3 - Immediate
	 */
	@Column(length = 1)
	private String priority;

	/**
	 * TD - TO DO, IP - In Progress, C - Completed
	 */
	@Column(length = 5)
	private String status;
	
	
	private String leadIds;

	@ManyToOne
	private UserDetail userDetail;


}
