package com.cbmachinery.aftercareserviceagent.report.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ReporKeysOutputDTO {

	private final List<BreakdownKeys> breakdownKeys;
	private final List<MaintainanceKeys> maintainanceKeys;

	@JsonCreator
	public ReporKeysOutputDTO(@JsonProperty("breakdownKeys") final List<BreakdownKeys> breakdownKeys,
			@JsonProperty("maintainanceKeys") final List<MaintainanceKeys> maintainanceKeys) {
		super();
		this.breakdownKeys = breakdownKeys;
		this.maintainanceKeys = maintainanceKeys;
	}

}
