package com.TicketStream.backend.listener;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.TicketStream.backend.model.Ticket;
import com.TicketStream.backend.service.BugReportService;
import com.TicketStream.backend.service.FeatureRequestService;
import com.TicketStream.backend.service.NetworkIssueService;
import com.TicketStream.backend.service.SystemOutageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TicketKafkaListeners {
    private final SystemOutageService systemOutageService;
    private final NetworkIssueService networkIssueService;
    private final FeatureRequestService featureRequestService;
    private final BugReportService bugReportService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TicketKafkaListeners(SystemOutageService systemOutageService,
                          NetworkIssueService networkIssueService,
                          BugReportService bugReportService,
                          FeatureRequestService featureRequestService,
                          ObjectMapper objectMapper) {
        this.systemOutageService = systemOutageService;
        this.networkIssueService = networkIssueService;
        this.featureRequestService = featureRequestService;
        this.bugReportService = bugReportService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "system-outage-tickets", groupId = "ticket-group")
    public void listenSystemOutage(String message, Consumer<?, ?> consumer) {
        try {
            // Deserialize the message into a Ticket object
            Ticket ticket = objectMapper.readValue(message, Ticket.class);
            System.out.println("Processing system outage ticket: " + ticket);
            pauseConsumer(consumer); // Pause processing for other messages
            try {
                systemOutageService.handleSystemOutage(ticket);
            } finally {
                resumeConsumer(consumer); // Resume after handling
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @KafkaListener(topics = "network-issues-tickets", groupId = "ticket-group")
    public void listenNetworkIssue(String message,  Consumer<?, ?> consumer) {
        try {
            // Deserialize the message into a Ticket object
            System.out.println("Came to network issues kafka topic listener");
            Ticket ticket = objectMapper.readValue(message, Ticket.class);
            System.out.println("Processing network issue ticket: " + ticket);
            String statusUpdateMessage = String.format("Ticket %s has been resolved.", ticket.getUuid());
            pauseConsumer(consumer); // Pause processing for other messages
            try {
                networkIssueService.handleNetworkIssue(ticket);
            } finally {
                resumeConsumer(consumer); // Resume after handling
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "feature-request-tickets", groupId = "ticket-group")
    public void listenFeatureRequest(String message) {
        try {
            // Deserialize the message into a Ticket object
            Ticket ticket = objectMapper.readValue(message, Ticket.class);
            System.out.println("Processing feature request ticket: " + ticket);
            featureRequestService.handleFeatureRequest(ticket);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    // normal-tickets listener - normal-tickets
    @KafkaListener(topics = "normal-tickets", groupId = "ticket-group")
    public void listenNormalRequest(String message) {
        try {
            // Deserialize the message into a Ticket object
            Ticket ticket = objectMapper.readValue(message, Ticket.class);
            System.out.println("Processing feature request ticket: " + ticket);
            bugReportService.handleBugReport(ticket);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    private void pauseConsumer(Consumer<?, ?> consumer) {
        consumer.pause(consumer.assignment());
        System.out.println("Paused System Outage Consumer for high-priority task.");
    }

    private void resumeConsumer(Consumer<?, ?> consumer) {
        consumer.resume(consumer.assignment());
        System.out.println("Resumed System Outage Consumer after handling high-priority task.");
    }
}