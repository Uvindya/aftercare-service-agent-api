package com.cbmachinery.aftercareserviceagent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginOutputDTO {
	private final String accessToken;
	private final String tokenType = "Bearer";
}
