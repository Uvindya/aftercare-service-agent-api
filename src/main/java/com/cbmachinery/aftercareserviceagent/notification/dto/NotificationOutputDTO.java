package com.cbmachinery.aftercareserviceagent.notification.dto;

import com.cbmachinery.aftercareserviceagent.notification.model.Category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationOutputDTO {

	private final long id;
	private final String title;
	private final String message;
	private final String owner;
	private final boolean isRead;
	private final Category category;
	private final String createdAt;
	private final String modifiedAt;
}
