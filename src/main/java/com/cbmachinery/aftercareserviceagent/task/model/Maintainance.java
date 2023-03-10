package com.cbmachinery.aftercareserviceagent.task.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.common.util.DateTimeUtil;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicTaskOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "maintainances")
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public class Maintainance extends Task {

	@Enumerated(EnumType.STRING)
	private MaintainanceType maintainanceType;

	@Enumerated(EnumType.STRING)
	private MaintainanceStatus status;
	
	private LocalDateTime approvedAt;

	@JsonIgnore
	public BasicMaintainanceOutputDTO viewAsBasicDTO() {
		BasicTaskOutputDTO task = super.viewAsBasicDTO();
		return new BasicMaintainanceOutputDTO(task.getId(), task.getDescription(), task.getReportedAt(),
				task.getScheduledDate(), task.getProductId(), task.getProductName(), task.getTechnicianId(),
				task.getTechnicianName(), maintainanceType, status, task.getClientId(), task.getClientName());
	}

	@JsonIgnore
	public MaintainanceOutputDTO viewAsDTO() {
		return new MaintainanceOutputDTO(getId(), DateTimeUtil.fomatToLongDateTime(createdAt), createdBy,
				DateTimeUtil.fomatToLongDateTime(modifiedAt), modifiedBy, getDescription(), getReportedAt(),
				getScheduledDate(), getTargetCompletionDate(), getProduct().viewAsDTO(),
				getTechnician() != null ? getTechnician().viewAsDTO() : null, getCompletionNote(), getAdditionalNote(),
				maintainanceType, status);
	}

}
