package com.TicketStream.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class Ticket {
    
    private String status = "pending"; // Default status is pending

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 200, message = "Critical tickets must have at least 200 characters in description.")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    private String priority; // This can be set internally based on category

    @NotBlank(message = "Email is required")
    private String email;    

    // New UUID field
    @Column(nullable = false, unique = true)
    private String uuid;

    // Constructor
    public Ticket() {
        this.status = "PENDING"; // Default status when a task is created
        this.uuid = UUID.randomUUID().toString(); // Generate a random UUID
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status; // Getter for status
    }

    public void markProcessing() {
        this.status = "PROCESSING";
    }

    public void markUnderReview() {
        this.status = "UNDER_REVIEW";
    }

    public void markRejected() {
        this.status = "REJECTED"; // Set status to REJECTED
    }

    public void markResolved() {
        this.status = "RESOLVED";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}