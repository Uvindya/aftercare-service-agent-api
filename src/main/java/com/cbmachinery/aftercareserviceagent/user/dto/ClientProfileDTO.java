package com.cbmachinery.aftercareserviceagent.user.dto;

import lombok.Getter;

@Getter
public class ClientProfileDTO {

	private final long id;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String primaryPhoneNo;
	private final String erpId;

	public ClientProfileDTO(long id, String firstName, String lastName, String email, String primaryPhoneNo,
			String erpId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.primaryPhoneNo = primaryPhoneNo;
		this.erpId = erpId;
	}

}
