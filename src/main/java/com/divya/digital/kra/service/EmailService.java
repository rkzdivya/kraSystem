package com.divya.digital.kra.service;

import java.io.File;
import java.io.IOException;

import jakarta.mail.MessagingException;

public interface EmailService {

	void sendEmailWithAttachment(String toEmail, String string, String string2, File tempFile) throws MessagingException, IOException;

	void sendSimpleMessage(String to, String subject, String text);

}
