package com.cbmachinery.aftercareserviceagent.user.dto;

import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ClientInputDTO extends UserInputDTO {

	private final String addressLine1;
	private final String addressLine2;
	private final String city;
	private final String district;
	private final String secondaryPhoneNo;

	@JsonCreator
	public ClientInputDTO(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
			@JsonProperty("email") String email, @JsonProperty("primaryPhoneNo") String primaryPhoneNo,
			@JsonProperty("gender") Gender gender, @JsonProperty("password") String password,
			@JsonProperty("addressLine1") String addressLine1, @JsonProperty("addressLine2") String addressLine2,
			@JsonProperty("city") String city, @JsonProperty("district") String district,
			@JsonProperty("secondaryPhoneNo") String secondaryPhoneNo) {
		super(firstName, lastName, email, primaryPhoneNo, gender, password);
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.district = district;
		this.secondaryPhoneNo = secondaryPhoneNo;
	}
}
