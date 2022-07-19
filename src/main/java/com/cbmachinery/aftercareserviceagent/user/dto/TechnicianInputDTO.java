package com.cbmachinery.aftercareserviceagent.user.dto;

import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class TechnicianInputDTO extends UserInputDTO {

	private final int yearOfExperience;

	@JsonCreator
	public TechnicianInputDTO(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
			@JsonProperty("email") String email, @JsonProperty("primaryPhoneNo") String primaryPhoneNo,
			@JsonProperty("gender") Gender gender, @JsonProperty("password") String password,
			@JsonProperty("yearOfExperience") int yearOfExperience) {
		super(firstName, lastName, email, primaryPhoneNo, gender, password);
		this.yearOfExperience = yearOfExperience;
	}
}
