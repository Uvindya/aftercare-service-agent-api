package com.cbmachinery.aftercareserviceagent.product.dto;

import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductOutputDTO {

	private final long id;
	private final String createdAt;
	private final String createdBy;
	private final String modifiedAt;
	private final String modifiedBy;
	private final String name;
	private final int warrentyPeriod;
	private final int maintainnanceInterval;
	private final String erpId;
	private final BasicUserOutputDTO client;

}
