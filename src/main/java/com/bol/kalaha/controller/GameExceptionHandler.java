package com.bol.kalaha.controller;

import com.bol.kalaha.entity.ApiResponse;
import com.bol.kalaha.exceptions.GameException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler for exceptions thrown by the API
 */
@RestControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {

        StringBuilder errors = new StringBuilder();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.append(violation.getMessage());
        }
        ApiResponse response = ApiResponse.builder()
                .error(errors.toString())
                .build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({GameException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleFunctionalException(GameException e) {
        ApiResponse error = ApiResponse.builder()
                .error(e.getMessage())
                .build();
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }
}