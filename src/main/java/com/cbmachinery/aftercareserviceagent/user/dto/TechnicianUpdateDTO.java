package com.cbmachinery.aftercareserviceagent.user.dto;

import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class TechnicianUpdateDTO {

	private final String firstName;
	private final String lastName;
	private final String primaryPhoneNo;
	private final Gender gender;
	private final int yearOfExperience;

	@JsonCreator
	public TechnicianUpdateDTO(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
			@JsonProperty("primaryPhoneNo") String primaryPhoneNo, @JsonProperty("gender") Gender gender,
			@JsonProperty("yearOfExperience") int yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
		this.firstName = firstName;
		this.lastName = lastName;
		this.primaryPhoneNo = primaryPhoneNo;
		this.gender = gender;
	}

}
