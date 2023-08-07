package com.syg.crm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class MeetingParticipant extends MasterEntity {

	@ManyToOne
	private Meeting meeting;

	@ManyToOne
	private UserDetail participant;
	
}
