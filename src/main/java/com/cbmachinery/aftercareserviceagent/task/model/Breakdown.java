package com.cbmachinery.aftercareserviceagent.task.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownType;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Priority;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Risk;

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

}
