package com.syg.crm.model;

import java.util.UUID;

import com.syg.crm.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User extends MasterEntity {

	@Column(unique = true, length = 150)
	private String userName;

	@Column(unique = true, length = 50)
	private String email;

//	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String token;

	@Column(length = 1)
	private Integer loggedIn = 0;

	@Enumerated(EnumType.STRING)
	@Column(length = 5)
	private UserType userType;

	@OneToOne
	private UserDetail userDetail;

	@ManyToOne
	private UserDetail createdUserDetail;

	@PreUpdate
	protected void updateToken() {
		if (loggedIn == 0) {
			token = null;
		} else {
			token = UUID.randomUUID().toString();
		}
	}

}
