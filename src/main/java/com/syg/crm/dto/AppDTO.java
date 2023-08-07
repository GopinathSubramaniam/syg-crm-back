package com.syg.crm.dto;

import com.syg.crm.enums.UserType;
import com.syg.crm.model.User;
import com.syg.crm.model.UserDetail;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppDTO {

	private User user;
	private UserDetail userDetail;

	private Long companyId;
	private Long branchId;
	private Long userDetailId;
	private String token;
	private String loggedInUser;
	private UserType userType;

	public AppDTO() {
	}

	public AppDTO(Long companyId, Long branchId, Long userDetailId, String token, String loggedInUser) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.userDetailId = userDetailId;
		this.token = token;
		this.loggedInUser = loggedInUser;
	}

}
