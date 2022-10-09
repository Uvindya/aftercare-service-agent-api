package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownType;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Priority;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Risk;

import lombok.Getter;

@Getter
public class BasicBreakdownOutputDTO extends BasicTaskOutputDTO {

	private final BreakdownType breakdownType;
	private final Risk riskLevel;
	private final Priority priorityLevel;
	private final BreakdownStatus status;

	public BasicBreakdownOutputDTO(long id, String description, LocalDate reportedAt, LocalDate scheduledDate,
			long productId, String productName, long technicianId, String technicianName, BreakdownType breakdownType,
			BreakdownStatus status, Priority priorityLevel, Risk riskLevel, long clientId, String clientName) {
		super(id, description, reportedAt, scheduledDate, productId, productName, technicianId, technicianName,
				clientId, clientName);
		this.breakdownType = breakdownType;
		this.status = status;
		this.priorityLevel = priorityLevel;
		this.riskLevel = riskLevel;
	}

}
