package com.cbmachinery.aftercareserviceagent.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BasicUserOutputDTO {

	private final long id;
	private final String name;
	private final String email;
	private final String primaryPhoneNo;
	private final boolean status;

}
