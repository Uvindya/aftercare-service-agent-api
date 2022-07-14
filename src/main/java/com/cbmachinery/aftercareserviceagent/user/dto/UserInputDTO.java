package com.cbmachinery.aftercareserviceagent.user.dto;

import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class UserInputDTO {

	private final String firstName;
	private final String lastName;
	private final String email;
	private final String primaryPhoneNo;
	private final Gender gender;
	private final String password;

	@JsonCreator
	public UserInputDTO(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
			@JsonProperty("email") String email, @JsonProperty("primaryPhoneNo") String primaryPhoneNo,
			@JsonProperty("gender") Gender gender, @JsonProperty("password") String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.primaryPhoneNo = primaryPhoneNo;
		this.gender = gender;
		this.password = password;
	}

}
