package io.github.renatompf.springelasticpoc.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;

/**
 * Exception handler for REST API.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Handle invalid UUID format in path variables
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String message = String.format("Invalid format for parameter '%s'", ex.getName());

        return ErrorResponse
                .builder(ex,HttpStatus.BAD_REQUEST,message)
                .type(URI.create(request.getRequestURI()))
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();
    }

    /**
     * Fallback handler for any uncaught exceptions (500 Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleUncaughtExceptions(Exception ex, HttpServletRequest request) {
        return ErrorResponse
                .builder(ex,HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred")
                .type(URI.create(request.getRequestURI()))
                .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
    }

}