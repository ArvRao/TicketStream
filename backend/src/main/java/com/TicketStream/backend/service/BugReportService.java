package com.TicketStream.backend.service;

import org.springframework.stereotype.Service;

import com.TicketStream.backend.model.Ticket;

@Service
public class BugReportService {

    public void handleBugReport(Ticket ticket) {
        // Logic to handle bug report tickets
        System.out.println("Handling bug report ticket: " + ticket);
        // Add your processing logic here (e.g., notify teams, log issues)
    }
}