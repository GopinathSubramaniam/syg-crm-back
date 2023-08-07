package com.syg.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.syg.crm.dto.EmailDTO;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public void sendSimpleMail(EmailDTO details) {

		try {

			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getTo());
			if (details.getCc() != null && details.getCc().length > 0)
				mailMessage.setCc(details.getCc());

			mailMessage.setSubject(details.getSubject());
			mailMessage.setText(details.getMsgBody());

			javaMailSender.send(mailMessage);
			System.out.println("Mail Sent Successfully...");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while Sending Mail");
		}
	}

}
