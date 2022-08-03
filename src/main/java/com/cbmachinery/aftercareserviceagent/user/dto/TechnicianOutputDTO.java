package com.cbmachinery.aftercareserviceagent.user.dto;

import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;

import lombok.Getter;

@Getter
public class TechnicianOutputDTO extends UserOutputDTO {

	private final int yearOfExperience;

	public TechnicianOutputDTO(long id, String createdAt, String createdBy, String modifiedAt, String modifiedBy,
			String firstName, String lastName, String email, String primaryPhoneNo, Gender gender, int yearOfExperience,
			String erpId) {
		super(id, createdAt, createdBy, modifiedAt, modifiedBy, firstName, lastName, email, primaryPhoneNo, gender,
				erpId);
		this.yearOfExperience = yearOfExperience;
	}

}
