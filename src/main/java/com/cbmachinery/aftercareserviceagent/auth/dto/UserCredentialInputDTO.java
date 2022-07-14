package com.cbmachinery.aftercareserviceagent.auth.dto;

import com.cbmachinery.aftercareserviceagent.auth.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class UserCredentialInputDTO {
	private final String username;
	private final String password;
	private final Role role;

	@JsonCreator
	public UserCredentialInputDTO(@JsonProperty("username") String username, @JsonProperty("password") String password,
			@JsonProperty("role") Role role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

}
