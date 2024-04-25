package com.bricedenice59.chatop.controller;


import com.bricedenice59.chatop.models.requests.LoginUserRequest;
import com.bricedenice59.chatop.models.requests.RegisterUserRequest;
import com.bricedenice59.chatop.models.responses.AuthenticationResponse;
import com.bricedenice59.chatop.models.responses.UserResponse;
import com.bricedenice59.chatop.services.AuthenticationService;
import com.bricedenice59.chatop.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
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

    /**
     * Post - Login a user
     */
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getCurrentUser(WebRequest request) {
        var userEmail = (String) request.getAttribute("currentUser_email", WebRequest.SCOPE_REQUEST);
        var user = userService.getUserByEmail(userEmail);

        var dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);

        var userResponse = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .created_at(user.getCreatedAt().format(dateTimeFormatter))
                .build();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
