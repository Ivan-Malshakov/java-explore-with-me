package ru.practicum.ewm.stats.error;

public class ErrorResponse {
    String error;
    String description;
    int status;

    public ErrorResponse(String error, String description, int status) {
        this.error = error;
        this.description = description;
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }
}