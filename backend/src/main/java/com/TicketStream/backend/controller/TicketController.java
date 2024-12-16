package com.TicketStream.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.TicketStream.backend.model.Ticket;
import com.TicketStream.backend.response.ResponseMessage;
import com.TicketStream.backend.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Endpoint to submit a new ticket
    @PostMapping("/submit")
    public ResponseEntity<ResponseMessage> submitTicket(@Validated @RequestBody Ticket ticket) {
        ticketService.processTicket(ticket);
        // return ResponseEntity.status(HttpStatus.CREATED).body(");
        String successMessage = "Ticket has been submitted successfully!" + " Id:" + ticket.getUuid();
        // Return as JSON
        return ResponseEntity.ok(new ResponseMessage(successMessage));
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
    public Page<Ticket> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ticketService.getAllTickets(page, size);
    }
}