package com.cbmachinery.aftercareserviceagent.email.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.email.dto.Email;
import com.cbmachinery.aftercareserviceagent.email.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender emailSender;
	private final String from;

	public EmailServiceImpl(final JavaMailSender emailSender, @Value("${spring.mail.from}") final String from) {
		super();
		this.emailSender = emailSender;
		this.from = from;
	}

	@Override
	@Async
	public void sendPlainEmail(Email email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getBody());

		this.emailSender.send(message);
	}

	@Override
	@Async
	public void sendHtmlEmail(Email email) {

		try {

			MimeMessage mimeMessage = this.emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

			helper.setText(email.getBody(), true);
			helper.setTo(email.getTo());
			helper.setSubject(email.getSubject());
			helper.setFrom(from);

			this.emailSender.send(mimeMessage);
		} catch (MessagingException e) {
			log.error(e.getLocalizedMessage());
		}
	}

}
