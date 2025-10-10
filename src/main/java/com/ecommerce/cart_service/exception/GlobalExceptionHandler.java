package com.ecommerce.cart_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CartException.class)
    public ResponseEntity<String> handleCartException(CartException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
