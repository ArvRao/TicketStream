package com.TicketStream.backend.service;

import org.springframework.stereotype.Service;

import com.TicketStream.backend.model.Ticket;

@Service
public class NetworkIssueService {

    public void handleNetworkIssue(Ticket ticket) {
        // Logic to handle network issue tickets
        System.out.println("Handling network issue ticket: " + ticket);
        // Add your processing logic here (e.g., notify teams, log issues)
    }
}