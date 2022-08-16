package com.cbmachinery.aftercareserviceagent.task.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class TechnicianTaskAssignmentDTO {

	private final long taskId;
	private final long technicianId;

	@JsonCreator
	public TechnicianTaskAssignmentDTO(@JsonProperty("taskId") long taskId,
			@JsonProperty("technicianId") long technicianId) {
		super();
		this.taskId = taskId;
		this.technicianId = technicianId;
	}

}
