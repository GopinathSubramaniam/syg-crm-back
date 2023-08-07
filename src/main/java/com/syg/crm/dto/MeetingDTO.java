package com.syg.crm.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MeetingDTO {

	private Long id;
	private String meetingId;
	private String subject;
	private String description;
	private Date startTime;
	private Date endTime;
	private String place;
	private Integer reminderMins;
	private Long host;

	private List<Long> participants = new ArrayList<>();

	private List<Long> removedParticipants = new ArrayList<>();

}
