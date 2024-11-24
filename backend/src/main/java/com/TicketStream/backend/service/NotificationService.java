package com.TicketStream.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.TicketStream.backend.model.Ticket;

@Service
public class NotificationService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; // For sending notifications

    public void notifyUser(Ticket ticket) {
        String notificationMessage = "Your system outage ticket has been resolved: " + ticket.getTitle();
        
        // Send notification message to a Kafka topic for notifications
        kafkaTemplate.send("user-notifications", notificationMessage);
        
        System.out.println("Notification sent: " + notificationMessage);
    }
}
