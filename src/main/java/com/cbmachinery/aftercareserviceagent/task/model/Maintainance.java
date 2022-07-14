package com.cbmachinery.aftercareserviceagent.task.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceType;

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

}
