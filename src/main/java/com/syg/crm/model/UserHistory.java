package com.syg.crm.model;

import com.syg.crm.enums.Operation;
import com.syg.crm.enums.Screen;

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

	private String field;

	@Column(columnDefinition = "text")
	private String value;

	@Enumerated(EnumType.STRING)
	private Screen screen;

	@Enumerated(EnumType.STRING)
	private Operation operation = Operation.U;

	private Long dataId; // Modified object id

	public UserHistory() {
	}

	public UserHistory(Screen screen) {
		this.screen = screen;
	}

	/**
	 * 
	 * @param dataId
	 * @param field
	 * @param value
	 * @param screen
	 * @param operation
	 */
	public UserHistory(Long dataId, String field, String value, Screen s, Operation o) {
		this.field = field;
		this.value = value;
		this.screen = s;
		this.operation = o;
		this.dataId = dataId;
	}

	/**
	 * 
	 * @param dataId
	 * @param field
	 * @param value
	 * @param screen
	 */
	public UserHistory(Long dataId, String field, String value, Screen s) {
		this.field = field;
		this.value = value;
		this.screen = s;
		this.dataId = dataId;
	}
	
	/**
	 * 
	 * @param dataId
	 * @param field
	 * @param value
	 * @param screen
	 */
	public UserHistory(Long dataId, String field, Screen s) {
		this.field = field;
		this.screen = s;
		this.dataId = dataId;
	}

}
