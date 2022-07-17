package com.cbmachinery.aftercareserviceagent.user.dto;

import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;

import lombok.Getter;

@Getter
public class ClientOutputDTO extends UserOutputDTO {

	private final String addressLine1;
	private final String addressLine2;
	private final String city;
	private final String district;
	private final String secondaryPhoneNo;

	public ClientOutputDTO(long id, String createdAt, String createdBy, String modifiedAt, String modifiedBy,
			String firstName, String lastName, String email, String primaryPhoneNo, Gender gender, String addressLine1,
			String addressLine2, String city, String district, String secondaryPhoneNo) {
		super(id, createdAt, createdBy, modifiedAt, modifiedBy, firstName, lastName, email, primaryPhoneNo, gender);
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.district = district;
		this.secondaryPhoneNo = secondaryPhoneNo;
	}

}
