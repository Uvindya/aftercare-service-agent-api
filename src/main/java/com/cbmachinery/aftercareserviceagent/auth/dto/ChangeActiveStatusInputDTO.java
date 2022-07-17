package com.cbmachinery.aftercareserviceagent.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ChangeActiveStatusInputDTO {

	private final String username;
	private final boolean status;

	@JsonCreator
	public ChangeActiveStatusInputDTO(@JsonProperty("username") String username,
			@JsonProperty("status") boolean status) {
		super();
		this.username = username;
		this.status = status;
	}

}
