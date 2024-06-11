package ru.practicum.ewm.stats.error;

import lombok.Data;

@Data
public class ErrorResponse {
    String error;
    String description;
    int status;

    public ErrorResponse(String error, String description, int status) {
        this.error = error;
        this.description = description;
        this.status = status;
    }
}