package com.syg.crm.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class UpdateObjDTO {

	private Long id;
	private String field;
	private String value;

	public UpdateObjDTO() {

	}

	public UpdateObjDTO(Long id, String field, String value) {
		this.id = id;
		this.field = field;
		this.value = value;
	}

}
