package com.cbmachinery.aftercareserviceagent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.common.audit.Auditable;
import com.cbmachinery.aftercareserviceagent.dto.UserCredentialOutputDTO;
import com.cbmachinery.aftercareserviceagent.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_credentials")
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public class UserCredential extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_credentials_sequence")
	@SequenceGenerator(name = "user_credentials_sequence", allocationSize = 20)
	private long id;

	@NotBlank
	@Size(max = 20)
	@Column(unique = true)
	private String username;

	@NotBlank
	@Size(max = 120)
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder.Default
	private boolean active = true;
	
	@JsonIgnore
	public UserCredentialOutputDTO viewAsOutput() {
		return new UserCredentialOutputDTO(id, username, role);
	}

}
