package com.cbmachinery.aftercareserviceagent.task.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.common.util.DateTimeUtil;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicBreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicTaskOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownType;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Priority;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Risk;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "breakdowns")
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public class Breakdown extends Task {

	@Enumerated(EnumType.STRING)
	private BreakdownType breakdownType;

	@Enumerated(EnumType.STRING)
	private Risk riskLevel;

	@Enumerated(EnumType.STRING)
	private Priority priorityLevel;

	@Enumerated(EnumType.STRING)
	private BreakdownStatus status;

	@Column(columnDefinition = "TEXT")
	private String rootCause;

	@Column(columnDefinition = "TEXT")
	private String solution;

	@JsonIgnore
	public BasicBreakdownOutputDTO viewAsBasicDTO() {
		BasicTaskOutputDTO task = super.viewAsBasicDTO();
		return new BasicBreakdownOutputDTO(task.getId(), task.getDescription(), task.getReportedAt(),
				task.getScheduledDate(), task.getProductId(), task.getProductName(), task.getTechnicianId(),
				task.getTechnicianName(), breakdownType, status, priorityLevel, riskLevel, task.getClientId(),
				task.getClientName());
	}

	@JsonIgnore
	public BreakdownOutputDTO viewAsDTO() {
		return new BreakdownOutputDTO(getId(), DateTimeUtil.fomatToLongDateTime(createdAt), createdBy,
				DateTimeUtil.fomatToLongDateTime(modifiedAt), modifiedBy, getDescription(), getReportedAt(),
				getScheduledDate(), getTargetCompletionDate(), getProduct().viewAsDTO(),
				getTechnician() != null ? getTechnician().viewAsDTO() : null, getCompletionNote(), getAdditionalNote(),
				breakdownType, status, riskLevel, priorityLevel, rootCause, solution);
	}

}
