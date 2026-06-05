package com.divya.digital.kra.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.divya.digital.kra.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Value("${spring.sendgrid.api-key}")
	private String sendGridApiKey;

	@Value("${spring.sendgrid.sender-email}")
	private String senderEmail;

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendEmailWithAttachment(String to, String subject, String text, File file)
			throws MessagingException, IOException {
		log.info("creating mime message");
		MimeMessage message = mailSender.createMimeMessage();
		log.info("Mime message created");
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		helper.addAttachment(file.getName(), file);
		log.info("helper:" + helper);
		log.info("message:" + message);
		mailSender.send(message);
		log.info("mail sent successfully");
	}

	
//	public void sendEmailWithAttachment1(String to, String subject, String text, File file) throws IOException {
//		log.info("Creating SendGrid email");
//
//		Email from = new Email(senderEmail);
//		Email toEmail = new Email(to);
//		Content content = new Content("text/plain", text);
//		Mail mail = new Mail(from, subject, toEmail, content);
//
//		Attachments attachments = new Attachments();
//		attachments.setFilename(file.getName());
//		attachments.setType(Files.probeContentType(file.toPath()));
//		attachments.setDisposition("attachment");
//		attachments.setContent(Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath())));
//		mail.addAttachments(attachments);
//
//		SendGrid sg = new SendGrid(sendGridApiKey);
//		Request request = new Request();
//		try {
//			request.setMethod(Method.POST);
//			request.setEndpoint("mail/send");
//			request.setBody(mail.build());
//			Response response = sg.api(request);
//			log.info("Response status code: " + response.getStatusCode());
//			log.info("Response body: " + response.getBody());
//			log.info("Response headers: " + response.getHeaders());
//		} catch (IOException ex) {
//			throw ex;
//		}
//	}
	
	

	    public void sendSimpleMessage(String to, String subject, String text) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(text);
	        mailSender.send(message);
	    }

}
