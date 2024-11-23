package com.TicketStream.backend.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.TicketStream.backend.model.Ticket;
import com.TicketStream.backend.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TicketKafkaListeners {

    private final TicketService ticketService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TicketKafkaListeners(TicketService ticketService) {
        this.ticketService = ticketService;
        this.objectMapper = new ObjectMapper(); // Initialize ObjectMapper
    }

    @KafkaListener(topics = "system-outage-tickets", groupId = "ticket-group")
    public void listenSystemOutage(String message) {
        try {
            // Deserialize the message into a Ticket object
            Ticket ticket = objectMapper.readValue(message, Ticket.class);
            System.out.println("Processing system outage ticket: " + ticket);
            // Process the ticket using the service
            ticketService.processTicket(ticket);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @KafkaListener(topics = "network-issue-tickets", groupId = "ticket-group")
    public void listenNetworkIssue(String message) {
        try {
            // Deserialize the message into a Ticket object
            Ticket ticket = objectMapper.readValue(message, Ticket.class);
            System.out.println("Processing network issue ticket: " + ticket);
            // Process the ticket using the service
            ticketService.processTicket(ticket);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @KafkaListener(topics = "feature-request-tickets", groupId = "ticket-group")
    public void listenFeatureRequest(String message) {
        try {
            // Deserialize the message into a Ticket object
            Ticket ticket = objectMapper.readValue(message, Ticket.class);
            System.out.println("Processing feature request ticket: " + ticket);
            // Process the ticket using the service
            ticketService.processTicket(ticket);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
}