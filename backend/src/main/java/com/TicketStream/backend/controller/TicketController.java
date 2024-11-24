package com.TicketStream.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.TicketStream.backend.model.Ticket;
import com.TicketStream.backend.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Endpoint to submit a new ticket
    @PostMapping("/submit")
    public ResponseEntity<String> submitTicket(@Validated @RequestBody Ticket ticket) {
        ticketService.processTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ticket has been submitted successfully!" + " Id:" + ticket.getId());
    }

    // Endpoint to retrieve a ticket by ID
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint to retrieve all tickets
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }
}