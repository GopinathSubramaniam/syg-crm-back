package com.syg.crm.model;

import com.syg.crm.enums.Operation;
import com.syg.crm.enums.Page;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class UserHistory extends MasterEntity {

	private String column;

	@Column(columnDefinition = "text")
	private String value;

	@Enumerated(EnumType.STRING)
	private Page page;

	@Enumerated(EnumType.STRING)
	private Operation operation;

	private Long dataId; // Modified object id

}
