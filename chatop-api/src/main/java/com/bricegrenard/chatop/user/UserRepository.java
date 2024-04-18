package com.bricegrenard.chatop.user;

import com.bricegrenard.chatop.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

}
