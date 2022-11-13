package com.cbmachinery.aftercareserviceagent.notification.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.notification.dto.NotificationCountDTO;
import com.cbmachinery.aftercareserviceagent.notification.dto.NotificationOutputDTO;
import com.cbmachinery.aftercareserviceagent.notification.model.Notification;
import com.cbmachinery.aftercareserviceagent.notification.repository.NotificationRepository;
import com.cbmachinery.aftercareserviceagent.notification.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;

	public NotificationServiceImpl(final NotificationRepository notificationRepository) {
		super();
		this.notificationRepository = notificationRepository;
	}

	@Override
	public List<Notification> save(List<Notification> notifications) {
		return this.notificationRepository.saveAll(notifications);
	}

	@Override
	public List<NotificationOutputDTO> findMyOwns(String owner) {
		return this.notificationRepository.findByOwnerOrderByCreatedAtDesc(owner).stream().map(Notification::viewAsDTO)
				.collect(Collectors.toList());
	}

	@Override
	public NotificationCountDTO myCount(String owner) {
		return new NotificationCountDTO(this.notificationRepository.countByOwnerAndIsReadFalse(owner));
	}

	@Transactional
	@Override
	public NotificationOutputDTO markasRead(long id) {
		this.notificationRepository.changeReadStatus(id, true, LocalDateTime.now());
		return this.notificationRepository.findById(id).get().viewAsDTO();
	}

}
