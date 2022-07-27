package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceType;

import lombok.Getter;

@Getter
public class BasicMaintainanceOutputDTO extends BasicTaskOutputDTO {

	private final MaintainanceType maintainanceType;
	private final MaintainanceStatus status;

	public BasicMaintainanceOutputDTO(long id, String description, LocalDate reportedAt, LocalDate scheduledDate,
			long productId, String productName, long technicianId, String technicianName,
			MaintainanceType maintainanceType, MaintainanceStatus status, long clientId, String clientName) {
		super(id, description, reportedAt, scheduledDate, productId, productName, technicianId, technicianName,
				clientId, clientName);
		this.maintainanceType = maintainanceType;
		this.status = status;
	}

}
