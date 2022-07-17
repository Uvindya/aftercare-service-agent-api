package com.cbmachinery.aftercareserviceagent.user.dto;

import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserOutputDTO {

	private final long id;
	private final String createdAt;
	private final String createdBy;
	private final String modifiedAt;
	private final String modifiedBy;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String primaryPhoneNo;
	private final Gender gender;

}
