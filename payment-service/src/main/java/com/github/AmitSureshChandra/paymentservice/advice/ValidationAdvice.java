package com.github.AmitSureshChandra.paymentservice.advice;

import jakarta.validation.ValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationAdvice {
    @ExceptionHandler(ValidationException.class)
    String handle(ValidationException exception) {
        return exception.getMessage();
    }
}
