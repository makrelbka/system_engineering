package com.flowershop.config;

import com.flowershop.model.User;
import com.flowershop.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AdminDataInitializer {
    private final UserRepository userRepository;

    public AdminDataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initAdmin() {
        if (userRepository.findByEmail("admin@flowershop.ru").isPresent()) {
            return;
        }

        User admin = new User(
                "admin@flowershop.ru",
                "admin",
                "Администратор",
                "+7 (495) 123-45-67",
                User.UserRole.ADMIN
        );
        userRepository.save(admin);
    }
}
