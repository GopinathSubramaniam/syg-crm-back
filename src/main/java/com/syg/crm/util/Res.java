package com.syg.crm.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Res {

	private Boolean success;
	private Integer statusCode;
	private String statusMsg;
	private Object data;

	public Res() {
		this.success = true;
		this.statusCode = 200;
	}

	public Res(Object data) {
		this.success = true;
		this.statusCode = 200;
		this.data = data;
	}

	public Res(Boolean success, String statusMsg) {
		this.success = success;
		if (success)
			this.statusCode = 500;
		this.statusMsg = statusMsg;
	}

	public Res(Boolean success, Integer statusCode) {
		this.success = success;
		this.statusCode = statusCode;
	}

	public Res(Boolean success, Integer statusCode, String statusMsg) {
		this.success = success;
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
	}

	public Res(Boolean success, Integer statusCode, Object data) {
		this.success = success;
		this.statusCode = statusCode;
		this.data = data;
	}

}
