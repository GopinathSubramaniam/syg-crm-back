package com.syg.crm.dto;

import com.syg.crm.model.Company;
import com.syg.crm.model.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDTO {

	private Company company;
	private User user;

}
