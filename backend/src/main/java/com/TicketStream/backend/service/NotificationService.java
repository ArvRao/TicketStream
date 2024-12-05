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
        System.out.println("ticket: " + ticket.getEmail());
        String statusUpdateMessage = String.format("%s|%s", ticket.getEmail(), ticket.getUuid());
        kafkaTemplate.send("user-notifications", statusUpdateMessage);
        
        System.out.println("Notification sent: " + statusUpdateMessage);
    }
}
