package com.cbmachinery.aftercareserviceagent.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbmachinery.aftercareserviceagent.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
