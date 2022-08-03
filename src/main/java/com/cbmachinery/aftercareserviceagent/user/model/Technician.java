package com.cbmachinery.aftercareserviceagent.user.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.common.util.DateTimeUtil;
import com.cbmachinery.aftercareserviceagent.skill.model.Skill;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianOutputDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "technicians")
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public class Technician extends User {

	private int yearOfExperience;

	@OneToMany(mappedBy = "technician", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Breakdown> assignedBreakdowns;

	@OneToMany(mappedBy = "technician", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Maintainance> assignedMaintainances;

	@ManyToMany
	@JoinTable(name = "technician_skills", joinColumns = @JoinColumn(name = "technician_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private Set<Skill> skills;

	@JsonIgnore
	public TechnicianOutputDTO viewAsDTO() {
		return new TechnicianOutputDTO(getId(), DateTimeUtil.fomatToLongDateTime(createdAt), createdBy,
				DateTimeUtil.fomatToLongDateTime(modifiedAt), modifiedBy, getFirstName(), getLastName(), getEmail(),
				getPrimaryPhoneNo(), getGender(), yearOfExperience, getErpId());
	}

}
