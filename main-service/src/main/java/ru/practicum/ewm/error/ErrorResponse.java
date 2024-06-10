package ru.practicum.ewm.error;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class ErrorResponse {
    private final List<String> errors;
    private final String description;
    private final String reason;
    private final String status;
    private final String timestamp;

    public ErrorResponse(List<String> errors, String description, String reason, String status) {
        this.errors = errors;
        this.description = description;
        this.reason = reason;
        this.status = status;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}