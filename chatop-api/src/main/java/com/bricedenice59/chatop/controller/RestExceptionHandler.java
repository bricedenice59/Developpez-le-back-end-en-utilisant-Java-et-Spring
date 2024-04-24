package com.bricedenice59.chatop.controller;

import com.bricedenice59.chatop.exceptions.RentalChangeOwnerForbiddenException;
import com.bricedenice59.chatop.exceptions.RentalNotFoundException;
import com.bricedenice59.chatop.exceptions.UserNotFoundException;
import com.bricedenice59.chatop.models.responses.ApiErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiErrorResponse> handleGeneralExceptions(Exception ex) {
        var errors = Set.of(ex.getMessage());
        var errorApiResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errors, LocalDateTime.now());
        return new ResponseEntity<>(errorApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UserNotFoundException.class, RentalNotFoundException.class, RentalChangeOwnerForbiddenException.class})
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(RuntimeException ex) {
        var errorApiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), StringUtils.join(ex.getStackTrace(), " "), LocalDateTime.now());
        return new ResponseEntity<>(errorApiResponse, HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Set<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toSet());
        var errorApiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), errors, LocalDateTime.now());
        return new ResponseEntity<>(errorApiResponse, HttpStatus.BAD_REQUEST) ;
    }
}
