package com.syg.crm.model;

import java.util.Date;

import com.syg.crm.enums.Operation;
import com.syg.crm.enums.Screen;
import com.syg.crm.util.Util;

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

	public UserHistory getModified(Task dbObj, Task newObj) {

		UserHistory uh = null;
		StringBuilder valBuilder = new StringBuilder();

		if (dbObj.getSubject() != null && !dbObj.getSubject().equals(newObj.getSubject())) {
			String val = "Subject: " + dbObj.getSubject() + "->" + newObj.getSubject() + ".  <br/>";
			valBuilder.append(val);
		}

		if (dbObj.getDescription() != null && !dbObj.getDescription().equals(newObj.getDescription())) {
			String val = "Description: " + dbObj.getDescription() + "->" + dbObj.getDescription() + ".  <br/>";
			valBuilder.append(val);
		}

		if (dbObj.getStartDate() != null
				&& !Util.formatDate(dbObj.getStartDate()).equals(Util.formatDate(newObj.getStartDate()))) {
			String val = "Start Date: " + dbObj.getStartDate() + "->" + Util.formatDate(dbObj.getStartDate());
			valBuilder.append(val);
			valBuilder.append(".<br/>");
		}

		if (dbObj.getDueDate() != null
				&& !Util.formatDate(dbObj.getDueDate()).equals(Util.formatDate(newObj.getDueDate()))) {
			String val = "Due Date: " + dbObj.getDueDate() + "->" + Util.formatDate(dbObj.getDueDate());
			valBuilder.append(val);
			valBuilder.append(".<br/>");
		}

		if (dbObj.getEndDate() != null
				&& !Util.formatDate(dbObj.getEndDate()).equals(Util.formatDate(newObj.getEndDate()))) {
			String val = "End Date: " + dbObj.getEndDate() + "->" + Util.formatDate(dbObj.getEndDate());
			valBuilder.append(val);
			valBuilder.append(".<br/>");
		}

		if (dbObj.getPriority() != null && !dbObj.getPriority().equals(newObj.getPriority()))
			valBuilder.append("Priority: " + dbObj.getPriority() + "->" + dbObj.getPriority() + ".  <br/>");

		if (dbObj.getStatus() != null && !dbObj.getStatus().equals(newObj.getStatus()))
			valBuilder.append("Status: " + dbObj.getStatus() + "->" + dbObj.getStatus() + ".  <br/>");

		if (dbObj.getLeadIds() != null && !dbObj.getLeadIds().equals(newObj.getLeadIds()))
			valBuilder.append("Updated Leads: " + dbObj.getLeadIds() + "->" + dbObj.getLeadIds() + ".  <br/>");

		if (!valBuilder.isEmpty()) {
			uh = new UserHistory(dbObj.getId(), "Object Updated", Screen.T);
			uh.setValue(valBuilder.toString());
			uh.setOperation(Operation.U);
		}

		return uh;
	}

}
