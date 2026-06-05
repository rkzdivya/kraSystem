package com.divya.digital.kra.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.divya.digital.kra.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(String token, String title, String body, String downloadUrl) {
        Message message = Message.builder()
            .putData("title", title)
            .putData("body", body)
            .putData("downloadUrl", downloadUrl)
            .setToken(token)
            .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}