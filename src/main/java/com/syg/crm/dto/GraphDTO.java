package com.syg.crm.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GraphDTO {

	private List<String> categories;
	private List<GraphSeriesDTO> series;

	public GraphDTO(List<String> categories, List<GraphSeriesDTO> series) {
		this.categories = categories;
		this.series = series;

	}
}
