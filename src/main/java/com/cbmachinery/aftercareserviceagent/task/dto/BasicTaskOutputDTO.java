package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BasicTaskOutputDTO {

	private final long id;
	private final String description;

	@JsonFormat(pattern = "MM/dd/yyyy")
	private final LocalDate reportedAt;

	@JsonFormat(pattern = "MM/dd/yyyy")
	private final LocalDate scheduledDate;

	private final long productId;
	private final String productName;
	private final long technicianId;
	private final String technicianName;

}
