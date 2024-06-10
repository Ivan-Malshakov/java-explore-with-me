package ru.practicum.ewm.stats.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import ru.practicum.ewm.stats.exception.BadRequestException;

import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerSQLIntegrityConstraintViolationException(final SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());
        return new ErrorResponse("SQL error", e.getMessage(), 409);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerValidationException(final ValidationException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Validation error", e.getMessage(), 400);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerHttpClientErrorException(final HttpClientErrorException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Validation error", e.getMessage(), 400);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerHttpServerErrorException(final HttpServerErrorException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Error", e.getMessage(), 500);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerDataAccessException(final DataAccessException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Error", e.getMessage(), 500);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadRequestException(final BadRequestException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Bad Request", e.getMessage(), 400);
    }
}