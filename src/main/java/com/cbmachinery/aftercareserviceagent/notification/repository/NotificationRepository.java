package com.cbmachinery.aftercareserviceagent.notification.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbmachinery.aftercareserviceagent.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByOwnerOrderByCreatedAtDesc(String owner);

	long countByOwnerAndIsReadFalse(String owner);

	@Modifying
	@Query("update Notification m set m.isRead =:isRead, m.modifiedAt=:modifiedAt where m.id =:id")
	void changeReadStatus(@Param("id") long id, @Param("isRead") boolean isRead,
			@Param("modifiedAt") LocalDateTime modifiedAt);
}
