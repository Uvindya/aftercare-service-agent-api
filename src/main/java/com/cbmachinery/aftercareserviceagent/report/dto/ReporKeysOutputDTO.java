package com.cbmachinery.aftercareserviceagent.report.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ReporKeysOutputDTO {

	private final List<BreakdownKeys> breakdownKeys;

	@JsonCreator
	public ReporKeysOutputDTO(@JsonProperty("breakdownKeys") final List<BreakdownKeys> breakdownKeys) {
		super();
		this.breakdownKeys = breakdownKeys;
	}

}
