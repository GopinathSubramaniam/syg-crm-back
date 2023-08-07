package com.syg.crm.util;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageRes {

	private Boolean success;
	private String msg;
	private Object data = new ArrayList<>();
	private Long totalRecords = 0L;
	private Integer totalPages = 0;

	public PageRes() {
		this.success = true;
	}

	public PageRes(Page<?> p) {
		this.success = true;

		if (p != null) {
			this.data = p.toList();
			this.totalPages = p.getTotalPages();
			this.totalRecords = p.getTotalElements();
		}
	}

	public PageRes(Object data) {
		this.success = true;
		this.data = data;
	}

	public PageRes(Boolean success, Integer statusCode) {
		this.success = success;
	}

	public PageRes(Boolean success, Integer statusCode, Object data) {
		this.success = success;
		this.data = data;
	}

}
