package com.cbmachinery.aftercareserviceagent.task.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.common.audit.Auditable;
import com.cbmachinery.aftercareserviceagent.notification.model.Notification;
import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicTaskOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.model.Client;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;
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
public abstract class Task extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_sequence")
	@SequenceGenerator(name = "tasks_sequence", allocationSize = 20)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String description;

	private LocalDate reportedAt;
	private LocalDateTime assignedAt;
	private LocalDateTime startedAt;
	private LocalDateTime completedAt;
	private LocalDate scheduledDate;
	private LocalDate targetCompletionDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "technician_id")
	private Technician technician;

	@OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Notification> notifications;

	@Column(columnDefinition = "TEXT")
	private String completionNote;

	@Column(columnDefinition = "TEXT")
	private String additionalNote;

	@JsonIgnore
	public BasicTaskOutputDTO viewAsBasicDTO() {
		long techId = 0;
		String techName = null;
		if (Objects.nonNull(technician)) {
			techId = technician.getId();
			techName = technician.getFullName();
		}
		Client client = product.getClient();
		return new BasicTaskOutputDTO(id, description, reportedAt, scheduledDate, product.getId(), product.getName(),
				techId, techName, client.getId(), client.getFullName());
	}

}
