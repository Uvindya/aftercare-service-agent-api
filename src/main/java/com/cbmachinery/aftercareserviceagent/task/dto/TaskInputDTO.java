package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class TaskInputDTO {

	private final String description;

	@JsonFormat(pattern = "MM/dd/yyyy")
	private final LocalDate scheduledDate;

	private final long productId;

	@JsonCreator
	public TaskInputDTO(@JsonProperty("description") String description,
			@JsonProperty("scheduledDate") LocalDate scheduledDate, @JsonProperty("productId") long productId) {
		super();
		this.description = description;
		this.scheduledDate = scheduledDate;
		this.productId = productId;
	}

}
