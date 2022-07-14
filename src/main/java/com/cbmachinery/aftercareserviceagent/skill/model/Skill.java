package com.cbmachinery.aftercareserviceagent.skill.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.user.model.Technician;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "skills")
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skills_sequence")
	@SequenceGenerator(name = "skills_sequence", allocationSize = 20)
	private long id;

	@Column(unique = true)
	private String description;

	@ManyToMany(mappedBy = "skills")
	private Set<Technician> technicians;

}
