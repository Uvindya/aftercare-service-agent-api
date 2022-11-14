package com.cbmachinery.aftercareserviceagent.email.service;

import com.cbmachinery.aftercareserviceagent.email.dto.Email;

public interface EmailService {
	void sendPlainEmail(Email email);

	void sendHtmlEmail(Email email);
}
