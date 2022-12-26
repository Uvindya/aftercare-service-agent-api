package com.cbmachinery.aftercareserviceagent.user.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.common.util.DateTimeUtil;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientProfileDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "clients")
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public class Client extends User {

	private String addressLine1;
	private String addressLine2;
	private String city;
	private String district;
	private String secondaryPhoneNo;

	@JsonIgnore
	public ClientOutputDTO viewAsDTO() {
		return new ClientOutputDTO(getId(), DateTimeUtil.fomatToLongDateTime(createdAt), createdBy,
				DateTimeUtil.fomatToLongDateTime(modifiedAt), modifiedBy, getFirstName(), getLastName(), getEmail(),
				getPrimaryPhoneNo(), getGender(), addressLine1, addressLine2, city, district, secondaryPhoneNo,
				getErpId());
	}

	public ClientProfileDTO viewAsProfile() {
		return new ClientProfileDTO(getId(), getFirstName(), getLastName(), getEmail(), getPrimaryPhoneNo(),
				getErpId());
	}

}
