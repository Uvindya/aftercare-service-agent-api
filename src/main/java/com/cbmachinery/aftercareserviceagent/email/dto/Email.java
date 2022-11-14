package com.cbmachinery.aftercareserviceagent.email.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Email {
	private final String to;
	private final String subject;
	private final String body;
}
