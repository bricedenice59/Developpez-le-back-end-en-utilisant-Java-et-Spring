package com.bricedenice59.chatop.services;

import com.bricedenice59.chatop.model.UserRequest;
import com.bricedenice59.chatop.user.User;
import com.bricedenice59.chatop.user.UserRepository;
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
