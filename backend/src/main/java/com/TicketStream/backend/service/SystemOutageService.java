package com.TicketStream.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TicketStream.backend.model.Ticket;
import com.TicketStream.backend.repository.TicketRepository;

@Service
public class SystemOutageService {
    
    @Autowired
    private NotificationService notificationService;
    private TicketRepository ticketRepository;
    

    public void handleSystemOutage(Ticket ticket) {
        // Logic to handle system outage tickets
        System.out.println("Handling system outage ticket: " + ticket);

        // Simulate resolving the issue
        // After resolving, update the ticket status
        ticket.markResolved();

        ticketRepository.save(ticket); // Persist changes to the database
        // Publish status update to Kafka
        // Send notification to user
        notificationService.notifyUser(ticket);
    }

    
}
