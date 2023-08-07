package com.syg.crm.dto;

import lombok.Data;

@Data
public class EmailDTO {

	private String[] to;
	private String[] cc;
	private String subject;
	private String msgBody;
	private String attachment;

	public EmailDTO(String[] to, String[] cc, String subject, String msgBody) {
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.msgBody = msgBody;
	}

}
