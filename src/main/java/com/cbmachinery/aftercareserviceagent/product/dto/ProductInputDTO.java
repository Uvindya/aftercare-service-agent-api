package com.cbmachinery.aftercareserviceagent.product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ProductInputDTO {

	private final String name;
	private final int warrentyPeriod;
	private final int maintainnanceInterval;
	private final String erpId;
	private final long clientId;

	@JsonCreator
	public ProductInputDTO(@JsonProperty("name") String name, @JsonProperty("warrentyPeriod") int warrentyPeriod,
			@JsonProperty("maintainnanceInterval") int maintainnanceInterval, @JsonProperty("erpId") String erpId,
			@JsonProperty("clientId") long clientId) {
		super();
		this.name = name;
		this.warrentyPeriod = warrentyPeriod;
		this.maintainnanceInterval = maintainnanceInterval;
		this.erpId = erpId;
		this.clientId = clientId;
	}

}
