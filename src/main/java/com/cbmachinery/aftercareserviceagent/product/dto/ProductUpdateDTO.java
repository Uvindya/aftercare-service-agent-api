package com.cbmachinery.aftercareserviceagent.product.dto;

import java.time.Year;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ProductUpdateDTO {
	private final String name;
	private final String description;
	private final String countryOfOrigin;
	private final String make;
	private final String model;
	private final Year manufactureYear;

	@JsonCreator
	public ProductUpdateDTO(@JsonProperty("name") String name, @JsonProperty("description") String description,
			@JsonProperty("countryOfOrigin") String countryOfOrigin, @JsonProperty("make") String make,
			@JsonProperty("model") String model, @JsonProperty("manufactureYear") Year manufactureYear) {
		super();
		this.name = name;
		this.description = description;
		this.countryOfOrigin = countryOfOrigin;
		this.make = make;
		this.model = model;
		this.manufactureYear = manufactureYear;
	}
}
