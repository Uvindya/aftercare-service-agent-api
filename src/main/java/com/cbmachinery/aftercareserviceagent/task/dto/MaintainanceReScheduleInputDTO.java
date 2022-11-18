package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class MaintainanceReScheduleInputDTO {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private final LocalDate scheduledDate;

	@JsonCreator
	public MaintainanceReScheduleInputDTO(@JsonProperty("scheduledDate") LocalDate scheduledDate) {
		super();
		this.scheduledDate = scheduledDate;
	}

}
