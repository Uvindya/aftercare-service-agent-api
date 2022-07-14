package com.cbmachinery.aftercareserviceagent.user.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

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

}
