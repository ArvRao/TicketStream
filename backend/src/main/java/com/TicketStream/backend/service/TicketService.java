package com.TicketStream.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// import javax.validation.Valid;
import java.util.List;

import com.TicketStream.backend.model.Ticket;
import com.TicketStream.backend.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; // Change to String for JSON serialization

    private final ObjectMapper objectMapper;

    @Autowired
    public TicketService() {
        this.objectMapper = new ObjectMapper(); // Initialize ObjectMapper
    }

    public void processTicket(@Valid Ticket ticket) {
        // Set priority based on category
        ticket.markProcessing();

        if (validateTicket(ticket)) {
            ticket.markUnderReview();
            if ("network issue".equalsIgnoreCase(ticket.getCategory()) ||
                    "system outage".equalsIgnoreCase(ticket.getCategory())) {
                ticket.setPriority("HIGH");
            } else {
                ticket.setPriority("NORMAL");
            }

            // Save the ticket to the database
            ticketRepository.save(ticket);
            // Send the ticket to Kafka topic
            // Serialize the ticket object to JSON string
            try {
                String ticketJson = objectMapper.writeValueAsString(ticket);
                String topic = determineTopic(ticket.getCategory());
                System.out.println("ticketJson: " + ticketJson);
                kafkaTemplate.send(topic, ticketJson); // Send JSON string to Kafka
            } catch (Exception e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        } else {
            ticket.markRejected();
            throw new IllegalArgumentException("Invalid ticket");
        }

        System.out.println("Ticket after validation before sending out notifications:" + ticket);

        // Notify user asynchronously after sending to Kafka, should we call here?
        // taskService.updateTaskStatus(task); 
    }
    
    private boolean validateTicket(Ticket ticket) {
        // Implement your validation logic here
        return ticket.getTitle() != null && !ticket.getTitle().isEmpty() &&
               ticket.getDescription() != null && !ticket.getDescription().isEmpty() &&
               ticket.getCategory() != null && !ticket.getCategory().isEmpty() &&
               ticket.getEmail() != null && !ticket.getEmail().isEmpty();
    }

    private String determineTopic(String category) {
        switch (category.toLowerCase()) {
            case "network issue":
                return "network-issues-tickets";
            case "system outage":
                return "system-outage-tickets";
            case "feature request":
                return "feature-request-tickets";
            default:
                return "normal-tickets"; // Fallback topic for unrecognized categories
        }
    }

    // Method to retrieve a ticket by ID
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    // Method to retrieve all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}