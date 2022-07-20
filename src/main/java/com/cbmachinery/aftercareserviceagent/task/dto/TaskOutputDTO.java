package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.cbmachinery.aftercareserviceagent.product.dto.ProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianOutputDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TaskOutputDTO {

	private final long id;
	private final String createdAt;
	private final String createdBy;
	private final String modifiedAt;
	private final String modifiedBy;
	private final String description;

	@JsonFormat(pattern = "MM/dd/yyyy")
	private final LocalDate reportedAt;

	@JsonFormat(pattern = "MM/dd/yyyy")
	private final LocalDate scheduledDate;

	@JsonFormat(pattern = "MM/dd/yyyy")
	private final LocalDate targetCompletionDate;

	private final ProductOutputDTO product;
	private final TechnicianOutputDTO technician;

	private final String completionNote;
	private final String additionalNote;

}
