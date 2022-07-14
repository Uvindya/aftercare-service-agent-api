package com.cbmachinery.aftercareserviceagent.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class LoginInputDTO {
	@NotBlank
	@NotNull
	private final String username;

	@NotBlank
	@NotNull
	private final String password;

	@JsonCreator
	public LoginInputDTO(@JsonProperty("username") String username, @JsonProperty("password") String password) {
		super();
		this.username = username;
		this.password = password;
	}
}
