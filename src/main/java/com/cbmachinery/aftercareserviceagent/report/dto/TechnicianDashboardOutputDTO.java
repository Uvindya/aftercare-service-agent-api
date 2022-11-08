package com.cbmachinery.aftercareserviceagent.report.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TechnicianDashboardOutputDTO {
	private final long totalTasks;
	private final long activeBreakdownsCount;
	private final long activeMaintainanceCount;
	private final long upCommingMaintainacesCount;
}
