package com.cbmachinery.aftercareserviceagent.user.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "administrators")
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public class Administrator extends User {

}
