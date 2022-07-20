package com.cbmachinery.aftercareserviceagent.task.dto;

import java.time.LocalDate;

import com.cbmachinery.aftercareserviceagent.product.dto.ProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceType;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianOutputDTO;

import lombok.Getter;

@Getter
public class MaintainanceOutputDTO extends TaskOutputDTO {

	private final MaintainanceType maintainanceType;
	private final MaintainanceStatus status;

	public MaintainanceOutputDTO(long id, String createdAt, String createdBy, String modifiedAt, String modifiedBy,
			String description, LocalDate reportedAt, LocalDate scheduledDate, LocalDate targetCompletionDate,
			ProductOutputDTO product, TechnicianOutputDTO technician, String completionNote, String additionalNote,
			MaintainanceType maintainanceType, MaintainanceStatus status) {
		super(id, createdAt, createdBy, modifiedAt, modifiedBy, description, reportedAt, scheduledDate,
				targetCompletionDate, product, technician, completionNote, additionalNote);
		this.maintainanceType = maintainanceType;
		this.status = status;
	}

}
