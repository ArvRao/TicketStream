package com.TicketStream.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.TicketStream.backend.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // You can define custom query methods here if needed
    List<Ticket> findByCategory(String category);

    // Custom query method using @Query annotation
    @Query("SELECT t FROM Ticket t WHERE t.priority = ?1")
    List<Ticket> findByPriority(String priority);

    // Custom query method using native SQL
    @Query(value = "SELECT * FROM tickets WHERE email = ?1", nativeQuery = true)
    Ticket findByEmail(String email);
}