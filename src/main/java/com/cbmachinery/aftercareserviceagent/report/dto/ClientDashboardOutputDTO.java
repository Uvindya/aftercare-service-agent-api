package com.cbmachinery.aftercareserviceagent.report.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClientDashboardOutputDTO {
	private final long productsCount;
	private final long activeBreakdownsCount;
	private final long waitingAcceptenceCount;
	private final long upCommingMaintainacesCount;
}
