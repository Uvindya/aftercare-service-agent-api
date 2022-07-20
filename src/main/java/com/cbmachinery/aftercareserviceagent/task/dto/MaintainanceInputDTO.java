package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class MaintainanceInputDTO extends TaskInputDTO {

	private final MaintainanceType maintainanceType;

	@JsonCreator
	public MaintainanceInputDTO(@JsonProperty("description") String description,
			@JsonProperty("scheduledDate") LocalDate scheduledDate, @JsonProperty("productId") long productId,
			@JsonProperty("maintainanceType") MaintainanceType maintainanceType) {
		super(description, scheduledDate, productId);
		this.maintainanceType = maintainanceType;
	}

}
