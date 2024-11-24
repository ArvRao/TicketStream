package com.TicketStream.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TicketStream.backend.model.Ticket;

@Service
public class NetworkIssueService {
    
    @Autowired
    private NotificationService notificationService;

    public void handleNetworkIssue(Ticket ticket) {

        System.out.println("Handling network issue ticket: " + ticket);
        // Add your processing logic here (e.g., notify teams, log issues)

        // Simulate resolving the issue
        // After resolving, update the ticket status
        ticket.markResolved();

        // Send notification to user
        notificationService.notifyUser(ticket);
    }
}