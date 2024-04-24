package com.bricedenice59.chatop.controller;


import com.bricedenice59.chatop.models.requests.LoginUserRequest;
import com.bricedenice59.chatop.models.requests.RegisterUserRequest;
import com.bricedenice59.chatop.models.responses.AuthenticationResponse;
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        authenticationService.registerUser(registerUserRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Post - Login a user
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationResponse> loginUser(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        var authenticationResponse = authenticationService.loginUser(loginUserRequest);
        return ResponseEntity.ok(authenticationResponse);
    }
}
