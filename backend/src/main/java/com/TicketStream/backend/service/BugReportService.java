package com.TicketStream.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TicketStream.backend.model.Ticket;

@Service
public class BugReportService {

    @Autowired
    private NotificationService notificationService;

    public void handleBugReport(Ticket ticket) {
        // Logic to handle bug report tickets
        System.out.println("Handling bug report ticket: " + ticket);

        ticket.markResolved();

        notificationService.notifyUser(ticket);
    }
}