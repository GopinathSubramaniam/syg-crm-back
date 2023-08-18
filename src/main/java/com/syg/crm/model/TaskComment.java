package com.syg.crm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class TaskComment extends MasterEntity {

	private String comment;
	
	@ManyToOne
	private Task task;

}
