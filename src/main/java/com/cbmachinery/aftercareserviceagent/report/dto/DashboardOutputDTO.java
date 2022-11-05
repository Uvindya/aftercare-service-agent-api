package com.cbmachinery.aftercareserviceagent.report.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DashboardOutputDTO {
	private final long productsCount;
	private final long clientsCount;
	private final long technicianCount;
	private final long tasksCount;
	private final long maintainancesCount;
	private final long breakdownsCount;
	private final long inprogressCount;
	private final long upCommingMaintainacesCount;
	private final long pendingBreakdownCount;
	private final long inprogressMaintainanceCount;
	private final long pendingAcceptenceBreakdownCount;
	private final double notStartedPercentage;
	private final double inprogressPercentage;
	private final double completedPercentage;
	private final List<MonthlySummaryDTO> monthlySummary;
}
