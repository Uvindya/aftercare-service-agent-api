package com.cbmachinery.aftercareserviceagent.notification.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbmachinery.aftercareserviceagent.notification.dto.NotificationCountDTO;
import com.cbmachinery.aftercareserviceagent.notification.dto.NotificationOutputDTO;
import com.cbmachinery.aftercareserviceagent.notification.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	private final NotificationService notificationService;

	public NotificationController(final NotificationService notificationService) {
		super();
		this.notificationService = notificationService;
	}

	@GetMapping("/my")
	public ResponseEntity<List<NotificationOutputDTO>> findMyOwns() {
		return ResponseEntity
				.ok(notificationService.findMyOwns(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	@GetMapping("/my/count")
	public ResponseEntity<NotificationCountDTO> myOwnCount() {
		return ResponseEntity
				.ok(notificationService.myCount(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<NotificationOutputDTO> markAsRead(@PathVariable long id) {
		return ResponseEntity.ok(notificationService.markasRead(id));
	}
}
