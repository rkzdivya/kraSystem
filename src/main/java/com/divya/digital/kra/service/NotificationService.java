package com.divya.digital.kra.service;

public interface NotificationService {

	void sendNotification(String token, String title, String body, String downloadUrl);

}
