package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.cbmachinery.aftercareserviceagent.product.dto.ProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownType;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Priority;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Risk;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianOutputDTO;

import lombok.Getter;

@Getter
public class BreakdownOutputDTO extends TaskOutputDTO {

	private final BreakdownType breakdownType;
	private final Risk riskLevel;
	private final Priority priorityLevel;
	private final BreakdownStatus status;
	private final String rootCause;
	private final String solution;

	public BreakdownOutputDTO(long id, String createdAt, String createdBy, String modifiedAt, String modifiedBy,
			String description, LocalDate reportedAt, LocalDate scheduledDate, LocalDate targetCompletionDate,
			ProductOutputDTO product, TechnicianOutputDTO technician, String completionNote, String additionalNote,
			BreakdownType breakdownType, BreakdownStatus status, Risk riskLevel, Priority priorityLevel,
			String rootCause, String solution) {
		super(id, createdAt, createdBy, modifiedAt, modifiedBy, description, reportedAt, scheduledDate,
				targetCompletionDate, product, technician, completionNote, additionalNote);
		this.breakdownType = breakdownType;
		this.status = status;
		this.riskLevel = riskLevel;
		this.priorityLevel = priorityLevel;
		this.rootCause = rootCause;
		this.solution = solution;
	}

}
