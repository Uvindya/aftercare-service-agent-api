package com.cbmachinery.aftercareserviceagent.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BasicProductOutputDTO {

	private final long id;
	private final String createdAt;
	private final String name;
	private final int warrentyPeriod;
	private final String erpId;
	private final long clientId;
	private final String clientName;

}
