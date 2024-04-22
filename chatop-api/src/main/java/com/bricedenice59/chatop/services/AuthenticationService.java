package com.bricedenice59.chatop.services;

import com.bricedenice59.chatop.models.UserRequest;
import com.bricedenice59.chatop.models.User;
import com.bricedenice59.chatop.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserRequest userRequest) {
        var user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .build();
        userRepository.save(user);
    }

    public Boolean loginUser(UserRequest userRequest) {
        var optionalUser = userRepository.findByEmail(userRequest.getEmail());
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            return user.getPassword().equals(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        }
        return false;
    }
}
