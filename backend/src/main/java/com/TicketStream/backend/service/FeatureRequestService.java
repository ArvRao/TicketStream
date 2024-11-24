package com.TicketStream.backend.service;

import org.springframework.stereotype.Service;

import com.TicketStream.backend.model.Ticket;

@Service
public class FeatureRequestService {
    public void handleFeatureRequest(Ticket ticket) {
        System.out.println("Handling feature requests: " + ticket);
        // Add your processing logic here (notify dev teams, log issues)
    }
}
