package com.syg.crm.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GraphData {

	private Date date;
	private String leadSource;
	private Long count;
	private Long total;

	public GraphData(String leadSource, Long count, Long total) {
		this.leadSource = leadSource;
		this.count = count;
		this.total = total;
	}

	public GraphData(Date date, String leadSource, Long count) {
		this.date = date;
		this.leadSource = leadSource;
		this.count = count;
	}
}
