package com.bricedenice59.chatop.controller;


import com.bricedenice59.chatop.models.requests.UserRequest;
import com.bricedenice59.chatop.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Post - Register a user
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {
        authenticationService.registerUser(userRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Post - Register a user
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserRequest userRequest) {
        var hasSucceeded = authenticationService.loginUser(userRequest);
        return (hasSucceeded) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
