package com.cbmachinery.aftercareserviceagent.product.dto;

import java.time.LocalDate;
import java.time.Year;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ProductInputDTO {

	private final String name;
	private final int warrentyPeriod;
	private final int maintainnanceInterval;
	private final String erpId;
	private final long clientId;
	private final String description;
	private final String countryOfOrigin;
	private final String make;
	private final String model;
	private final Year manufactureYear;
	private final String serialNumber;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private final LocalDate purchasedAt;

	private final boolean migrated;

	@JsonCreator
	public ProductInputDTO(@JsonProperty("name") String name, @JsonProperty("warrentyPeriod") int warrentyPeriod,
			@JsonProperty("maintainnanceInterval") int maintainnanceInterval, @JsonProperty("erpId") String erpId,
			@JsonProperty("clientId") long clientId, @JsonProperty("description") String description,
			@JsonProperty("countryOfOrigin") String countryOfOrigin, @JsonProperty("make") String make,
			@JsonProperty("model") String model, @JsonProperty("manufactureYear") Year manufactureYear,
			@JsonProperty("serialNumber") String serialNumber, @JsonProperty("purchasedAt") LocalDate purchasedAt,
			@JsonProperty("migrated") boolean migrated) {
		super();
		this.name = name;
		this.warrentyPeriod = warrentyPeriod;
		this.maintainnanceInterval = maintainnanceInterval;
		this.erpId = erpId;
		this.clientId = clientId;
		this.description = description;
		this.countryOfOrigin = countryOfOrigin;
		this.make = make;
		this.model = model;
		this.manufactureYear = manufactureYear;
		this.serialNumber = serialNumber;
		this.purchasedAt = purchasedAt;
		this.migrated = migrated;
	}

}
