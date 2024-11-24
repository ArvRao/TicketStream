package com.TicketStream.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TicketStream.backend.model.Ticket;

@Service
public class FeatureRequestService {
    
    @Autowired
    private NotificationService notificationService;

    public void handleFeatureRequest(Ticket ticket) {

        System.out.println("Handling feature requests: " + ticket);
        // Add your processing logic here (notify dev teams, log issues)

        // Simulate resolving the issue
        // After resolving, update the ticket status
        ticket.markResolved();

        // Send notification to user
        notificationService.notifyUser(ticket);
    }
}
