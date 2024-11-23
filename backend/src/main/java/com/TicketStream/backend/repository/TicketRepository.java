package com.TicketStream.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TicketStream.backend.model.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Derived query method to find tickets by category
    List<Ticket> findByCategory(String category);
    
    // Derived query method to find tickets by priority
    List<Ticket> findByPriority(String priority);
    
    // You can define more custom query methods as needed
}