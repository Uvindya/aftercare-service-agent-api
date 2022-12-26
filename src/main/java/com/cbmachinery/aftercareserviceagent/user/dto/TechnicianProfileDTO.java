package com.cbmachinery.aftercareserviceagent.user.dto;

import lombok.Getter;

@Getter
public class TechnicianProfileDTO {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String primaryPhoneNo;
	private String erpId;

	public TechnicianProfileDTO(long id, String firstName, String lastName, String email, String primaryPhoneNo,
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
