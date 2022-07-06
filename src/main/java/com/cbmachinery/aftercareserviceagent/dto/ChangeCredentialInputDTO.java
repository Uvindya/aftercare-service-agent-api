package com.cbmachinery.aftercareserviceagent.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ChangeCredentialInputDTO {

	@NotBlank
	@NotNull
	private final String currentPassword;

	@NotBlank
	@NotNull
	private final String newPassword;

	@JsonCreator
	public ChangeCredentialInputDTO(@JsonProperty("currentPassword") String currentPassword,
			@JsonProperty("newPassword") String newPassword) {
		super();
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}

}
