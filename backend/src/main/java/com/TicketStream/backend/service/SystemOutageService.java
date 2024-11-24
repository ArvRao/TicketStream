package com.TicketStream.backend.service;

import org.springframework.stereotype.Service;

import com.TicketStream.backend.model.Ticket;

@Service
public class SystemOutageService {

    public void handleSystemOutage(Ticket ticket) {
        // Logic to handle system outage tickets
        System.out.println("Handling system outage ticket: " + ticket);
        // Add your processing logic here (e.g., notify teams, log issues)
    }
}
