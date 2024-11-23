package com.TicketStream.backend.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseConnectionTest implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try {
            if (dataSource.getConnection() != null) {
                System.out.println("Database connection successful!");
            }
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
}