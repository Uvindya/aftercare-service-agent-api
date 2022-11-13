package com.cbmachinery.aftercareserviceagent.notification.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.common.audit.Auditable;
import com.cbmachinery.aftercareserviceagent.common.util.DateTimeUtil;
import com.cbmachinery.aftercareserviceagent.notification.dto.NotificationOutputDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notifications")
@Audited
@Getter
@NoArgsConstructor
@SuperBuilder
public class Notification extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_sequence")
	@SequenceGenerator(name = "notifications_sequence", allocationSize = 20)
	private long id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String message;

	private String owner;

	private boolean isRead;

	@Enumerated(EnumType.STRING)
	private Category category;

	@JsonIgnore
	public NotificationOutputDTO viewAsDTO() {
		return new NotificationOutputDTO(id, title, message, owner, isRead, category,
				DateTimeUtil.fomatToLongDateTime(createdAt), DateTimeUtil.fomatToLongDateTime(modifiedAt));
	}

}
