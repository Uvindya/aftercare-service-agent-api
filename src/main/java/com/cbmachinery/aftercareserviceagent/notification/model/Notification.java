package com.cbmachinery.aftercareserviceagent.notification.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.cbmachinery.aftercareserviceagent.common.audit.Auditable;
import com.cbmachinery.aftercareserviceagent.task.model.Task;
import com.cbmachinery.aftercareserviceagent.user.model.User;

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

	@Column(columnDefinition = "TEXT")
	private String message;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "task_id", nullable = false)
	private Task task;

}
