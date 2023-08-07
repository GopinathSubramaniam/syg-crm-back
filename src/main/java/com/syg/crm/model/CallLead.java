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
public class CallLead extends MasterEntity {

	@Column(length = 100)
	private String subject;

	private String purposeOfCall;

	@Column(length = 100)
	private String callTo;

	@Column(length = 1)
	private String callType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	@Column(length = 5)
	private String duration;

	/**
	 * None - N Interested - I, Not Interested - NI, No Response / Busy - NRB,
	 * Requested More Info - RMI, Requested Call Back - RCB, Invalid Number - IN
	 */
	@Column(length = 5)
	private String result;

	@ManyToOne
	private UserDetail userDetail;

}
