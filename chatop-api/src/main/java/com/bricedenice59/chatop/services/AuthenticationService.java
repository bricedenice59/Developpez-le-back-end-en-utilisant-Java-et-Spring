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

    public void registerUser(UserRequest userDto) {
        var user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                .build();
        userRepository.save(user);
    }
}
