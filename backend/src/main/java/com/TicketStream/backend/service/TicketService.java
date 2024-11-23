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

    @Autowired
    private JavaMailSender mailSender;

    private final ObjectMapper objectMapper;

    @Autowired
    public TicketService() {
        this.objectMapper = new ObjectMapper(); // Initialize ObjectMapper
    }

    public void processTicket(@Valid Ticket ticket) {
        // Set priority based on category
        if ("network issue".equalsIgnoreCase(ticket.getCategory()) || 
            "system outage".equalsIgnoreCase(ticket.getCategory())) {
            ticket.setPriority("HIGH");
        } else {
            ticket.setPriority("NORMAL");
        }

        // Save the ticket to the database
        ticketRepository.save(ticket);

        // Serialize the ticket object to JSON string
        try {
            String ticketJson = objectMapper.writeValueAsString(ticket);
            String topic = determineTopic(ticket.getCategory());
            kafkaTemplate.send(topic, ticketJson); // Send JSON string to Kafka
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        // Notify user asynchronously after sending to Kafka
        notifyUser(ticket);
    }

    private String determineTopic(String category) {
        switch (category.toLowerCase()) {
            case "system outage":
                return "system-outage-tickets";
            case "network issue":
                return "network-issue-tickets";
            case "feature request":
                return "feature-request-tickets";
            default:
                return "normal-tickets"; // Fallback topic for unrecognized categories
        }
    }

    private void notifyUser(Ticket ticket) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(ticket.getEmail());
        message.setSubject("Ticket Submitted Successfully");
        message.setText("Your ticket has been submitted with ID: " + ticket.getId() + 
                        "\nTitle: " + ticket.getTitle() + 
                        "\nCategory: " + ticket.getCategory() +
                        "\nPriority: " + ticket.getPriority());
        System.out.println("Ticket:" + " title: " + ticket.getTitle() + " description: " + ticket.getDescription());
        // mailSender.send(message);
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