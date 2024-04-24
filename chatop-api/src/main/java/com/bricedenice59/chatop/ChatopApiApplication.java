package com.bricedenice59.chatop;

import com.bricedenice59.chatop.models.Role;
import com.bricedenice59.chatop.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChatopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatopApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner (RoleRepository roleRepository) {
        return args -> {
                if(roleRepository.findByName("USER").isEmpty()) {
                    roleRepository.save(Role.builder().name("USER").build());
                }
        };
    }
}
