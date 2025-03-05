package com.example.tarea3;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionListener {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseConnectionListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void checkDatabaseConnection() {
        try {
            String databaseName = jdbcTemplate.queryForObject(
                "SELECT current_database()", String.class);
            
            System.out.println("=====================================================");
            System.out.println("✅ CONEXIÓN EXITOSA a la base de datos: " + databaseName);
            System.out.println("=====================================================");
        } catch (Exception e) {
            System.err.println("❌ ERROR al conectar con la base de datos: " + e.getMessage());
        }
    }
}