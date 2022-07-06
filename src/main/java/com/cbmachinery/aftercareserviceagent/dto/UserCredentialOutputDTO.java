package com.cbmachinery.aftercareserviceagent.dto;

import com.cbmachinery.aftercareserviceagent.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCredentialOutputDTO {
	private final long id;
	private final String username;
	private final Role role;

}
