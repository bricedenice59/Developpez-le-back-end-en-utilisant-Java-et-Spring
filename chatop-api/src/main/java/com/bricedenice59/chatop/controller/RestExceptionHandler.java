package com.bricedenice59.chatop.controller;

import com.bricedenice59.chatop.exceptions.RentalChangeOwnerForbiddenException;
import com.bricedenice59.chatop.exceptions.RentalNotFoundException;
import com.bricedenice59.chatop.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<?> handleRentalNotFoundException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RentalChangeOwnerForbiddenException.class)
    public ResponseEntity<?> handleRentalChangeOwnerException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
