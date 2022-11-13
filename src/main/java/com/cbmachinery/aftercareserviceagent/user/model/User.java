package com.cbmachinery.aftercareserviceagent.user.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.common.audit.Auditable;
import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public abstract class User extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
	@SequenceGenerator(name = "users_sequence", allocationSize = 20)
	private long id;

	private String firstName;
	private String lastName;
	private String email;
	private String primaryPhoneNo;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String erpId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_credential_id", referencedColumnName = "id")
	private UserCredential userCredential;

	@JsonIgnore
	public BasicUserOutputDTO viewAsBasicDTO() {
		return new BasicUserOutputDTO(id, firstName + " " + lastName, email, primaryPhoneNo, userCredential.isActive(),
				erpId);
	}

	@JsonIgnore
	public String getFullName() {
		return firstName + " " + lastName;
	}

}
