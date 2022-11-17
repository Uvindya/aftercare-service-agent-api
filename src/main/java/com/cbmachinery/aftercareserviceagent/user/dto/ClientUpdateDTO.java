package com.cbmachinery.aftercareserviceagent.user.dto;

import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ClientUpdateDTO {
	private final String firstName;
	private final String lastName;
	private final String primaryPhoneNo;
	private final Gender gender;
	private final String addressLine1;
	private final String addressLine2;
	private final String city;
	private final String district;
	private final String secondaryPhoneNo;

	@JsonCreator
	public ClientUpdateDTO(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
			@JsonProperty("primaryPhoneNo") String primaryPhoneNo, @JsonProperty("gender") Gender gender,
			@JsonProperty("addressLine1") String addressLine1, @JsonProperty("addressLine2") String addressLine2,
			@JsonProperty("city") String city, @JsonProperty("district") String district,
			@JsonProperty("secondaryPhoneNo") String secondaryPhoneNo) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.district = district;
		this.secondaryPhoneNo = secondaryPhoneNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.primaryPhoneNo = primaryPhoneNo;
		this.gender = gender;
	}
}
