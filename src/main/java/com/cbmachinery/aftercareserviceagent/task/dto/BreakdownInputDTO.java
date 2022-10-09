package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownType;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Priority;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Risk;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class BreakdownInputDTO extends TaskInputDTO {

	private final BreakdownType breakdownType;
	private final Risk riskLevel;
	private final Priority priorityLevel;
	private final BreakdownStatus status;
	private final String rootCause;
	private final String solution;

	@JsonCreator
	public BreakdownInputDTO(@JsonProperty("description") String description,
			@JsonProperty("scheduledDate") LocalDate scheduledDate, @JsonProperty("productId") long productId,
			@JsonProperty("breakdownType") BreakdownType breakdownType, @JsonProperty("riskLevel") Risk riskLevel,
			@JsonProperty("priorityLevel") Priority priorityLevel, @JsonProperty("status") BreakdownStatus status,
			@JsonProperty("rootCause") String rootCause, @JsonProperty("solution") String solution) {
		super(description, scheduledDate, productId);
		this.breakdownType = breakdownType;
		this.riskLevel = riskLevel;
		this.priorityLevel = priorityLevel;
		this.status = status;
		this.rootCause = rootCause;
		this.solution = solution;
	}

}
