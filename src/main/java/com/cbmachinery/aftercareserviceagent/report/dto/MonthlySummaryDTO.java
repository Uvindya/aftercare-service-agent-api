package com.cbmachinery.aftercareserviceagent.report.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MonthlySummaryDTO {
	private final String name;
	private final long maintainances;
	private final long breakdowns;
}
