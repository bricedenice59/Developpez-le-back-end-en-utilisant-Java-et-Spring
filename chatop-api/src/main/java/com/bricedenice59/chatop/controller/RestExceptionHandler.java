package com.bricedenice59.chatop.controller;

import com.bricedenice59.chatop.exceptions.RentalChangeOwnerForbiddenException;
import com.bricedenice59.chatop.exceptions.RentalNotFoundException;
import com.bricedenice59.chatop.exceptions.UserNotFoundException;
import com.bricedenice59.chatop.models.responses.ApiErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, RentalNotFoundException.class, RentalChangeOwnerForbiddenException.class})
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(RuntimeException ex) {
        var error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), StringUtils.join(ex.getStackTrace(), " "), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST) ;
    }
}
