package ru.practicum.ewm.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.CategoryNotEmptyException;
import ru.practicum.ewm.exception.ConflictRequestException;
import ru.practicum.ewm.exception.DataNotFoundException;

import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerSQLIntegrityConstraintViolationException(final SQLIntegrityConstraintViolationException e) {
        log.error("SQL Error: {}", e.getMessage());
        return new ErrorResponse(List.of("SQL Error"), e.getMessage(),
                HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerValidationException(final ValidationException e) {
        log.error("Validation Error: {}", e.getMessage());
        return new ErrorResponse(List.of("Validation Error"), e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerDataNotFoundException(final DataNotFoundException e) {
        log.error("Data Not Found: {}", e.getMessage());
        return new ErrorResponse(List.of("Data Not Found"), e.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerConflictRequestException(final ConflictRequestException e) {
        log.error("Conflict Request Error: {}", e.getMessage());
        return new ErrorResponse(List.of("Conflict Request Error"), e.getMessage(),
                HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadRequestException(final BadRequestException e) {
        log.error("Bad request: {}", e.getMessage());
        return new ErrorResponse(List.of("Bad Request"), e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerCategoryNotEmptyException(final CategoryNotEmptyException e) {
        log.error("Conflict Request Error: {}", e.getMessage());
        return new ErrorResponse(List.of("Category Not Empty"), e.getMessage(),
                HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.error("Conflict Request Error: {}", e.getMessage());
        return new ErrorResponse(List.of("Data Integrity Violation"), e.getMessage(),
                HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Bad request: {}", e.getMessage());
        return new ErrorResponse(List.of("Method Argument Not Valid"), e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.error("Bad request: {}", e.getMessage());
        return new ErrorResponse(List.of("Method Argument Type Mismatch"), e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.name());
    }
}