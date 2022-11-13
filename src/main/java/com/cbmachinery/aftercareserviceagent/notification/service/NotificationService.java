package com.cbmachinery.aftercareserviceagent.notification.service;

import java.util.List;

import com.cbmachinery.aftercareserviceagent.notification.dto.NotificationCountDTO;
import com.cbmachinery.aftercareserviceagent.notification.dto.NotificationOutputDTO;
import com.cbmachinery.aftercareserviceagent.notification.model.Notification;

public interface NotificationService {
	List<Notification> save(List<Notification> notifications);

	List<NotificationOutputDTO> findMyOwns(String owner);

	NotificationCountDTO myCount(String owner);

	NotificationOutputDTO markasRead(long id);
}
