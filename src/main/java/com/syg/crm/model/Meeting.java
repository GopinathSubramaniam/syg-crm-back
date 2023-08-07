package com.syg.crm.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Meeting extends MasterEntity {

	private String meetingId;
	private String subject;
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	private String place;
	private Integer reminderMins;

	@ManyToOne
	private UserDetail host;

	public Meeting() {
	}

	public Meeting(String subject, String description, Date startTime, Date endTime, String place, Integer reminderMins,
			UserDetail host) {

	}

}
