package com.cbmachinery.aftercareserviceagent.notification.sender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.auth.model.enums.Role;
import com.cbmachinery.aftercareserviceagent.auth.service.UserCredentialService;
import com.cbmachinery.aftercareserviceagent.notification.model.Category;
import com.cbmachinery.aftercareserviceagent.notification.model.Group;
import com.cbmachinery.aftercareserviceagent.notification.model.Notification;
import com.cbmachinery.aftercareserviceagent.notification.service.NotificationService;

@Component
public class NotificationSender {

	private final NotificationService notificationService;
	private final UserCredentialService userCredentialService;

	public NotificationSender(final NotificationService notificationService,
			final UserCredentialService userCredentialService) {
		super();
		this.notificationService = notificationService;
		this.userCredentialService = userCredentialService;
	}

	@Async
	public void send(String owner, String title, String message, Category category) {
		this.notificationService.save(List.of(Notification.builder().title(title).category(category).isRead(false)
				.message(message).owner(owner).build()));
	}

	@Async
	public void send(Group group, String title, String message, Category category) {
		List<String> users = new ArrayList<>();

		if (group.equals(Group.ADMINISTRATOR)) {
			users.addAll(this.userCredentialService.findByRole(Role.ADMIN).stream().map(UserCredential::getUsername)
					.collect(Collectors.toList()));
		}

		List<Notification> notifications = users.stream().map(u -> Notification.builder().title(title)
				.category(category).isRead(false).message(message).owner(u).build()).collect(Collectors.toList());

		this.notificationService.save(notifications);
	}

}
