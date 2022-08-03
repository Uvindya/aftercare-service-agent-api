package com.cbmachinery.aftercareserviceagent.product.model;

import java.time.Year;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.common.audit.Auditable;
import com.cbmachinery.aftercareserviceagent.common.util.DateTimeUtil;
import com.cbmachinery.aftercareserviceagent.product.dto.BasicProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.user.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products")
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public class Product extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_sequence")
	@SequenceGenerator(name = "products_sequence", allocationSize = 20)
	private long id;

	private String name;
	private int warrentyPeriod;
	private int maintainnanceInterval;
	private String erpId;

	@Column(columnDefinition = "TEXT")
	private String description;

	private String countryOfOrigin;
	private String make;
	private String model;
	private Year manufactureYear;
	private String serialNumber;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Breakdown> breakdowns;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Maintainance> maintainances;

	@JsonIgnore
	public BasicProductOutputDTO viewAsBasicDTO() {
		return new BasicProductOutputDTO(id, DateTimeUtil.fomatToLongDateTime(createdAt), name, warrentyPeriod, erpId,
				client.getId(), client.getFullName(), serialNumber);
	}

	@JsonIgnore
	public ProductOutputDTO viewAsDTO() {
		return new ProductOutputDTO(id, DateTimeUtil.fomatToLongDateTime(createdAt), createdBy,
				DateTimeUtil.fomatToLongDateTime(modifiedAt), modifiedBy, name, warrentyPeriod, maintainnanceInterval,
				erpId, client.viewAsBasicDTO(), description, countryOfOrigin, make, model, manufactureYear,
				serialNumber);
	}

}
