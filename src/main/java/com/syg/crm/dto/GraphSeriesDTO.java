package com.syg.crm.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class GraphSeriesDTO {

	private String date;
	private String name;
	private List<Long> data = new ArrayList<Long>();
}
