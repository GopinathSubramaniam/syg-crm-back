package com.syg.crm.dto;

import java.util.List;

import com.syg.crm.model.Lead;
import com.syg.crm.model.Task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class LeadDTO {
	private Lead lead;
	private List<Task> tasks;
}
