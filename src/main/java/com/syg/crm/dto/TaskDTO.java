package com.syg.crm.dto;

import java.util.List;

import com.syg.crm.model.Lead;
import com.syg.crm.model.Task;
import com.syg.crm.model.TaskComment;
import com.syg.crm.model.UserHistory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TaskDTO {

	private Task task;
	private List<TaskComment> comments;
	private List<Lead> leads;
	private List<UserHistory> histories;

}
