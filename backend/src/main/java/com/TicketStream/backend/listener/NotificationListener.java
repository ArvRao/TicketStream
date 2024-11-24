package com.TicketStream.backend.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @KafkaListener(topics = "user-notifications", groupId = "notification-group")
    public void listenNotifications(String message) {
        // Logic to send an email or real-time notification to the user
        sendEmailNotification(message);
    }

    public void sendEmailNotification(String message) {
        // Here you would implement your email sending logic
        // For example, using Spring Mail or any other email service
        System.out.println("Sending email notification: " + message);
        
        // Example: emailService.sendEmail(userEmail, "Ticket Resolved", message);
    }
}
