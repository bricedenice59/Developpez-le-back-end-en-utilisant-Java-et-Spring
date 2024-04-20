package com.bricedenice59.chatop.services;

import com.bricedenice59.chatop.exceptions.UserNotFoundException;
import com.bricedenice59.chatop.user.User;
import com.bricedenice59.chatop.user.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id) {
        var user =  userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
