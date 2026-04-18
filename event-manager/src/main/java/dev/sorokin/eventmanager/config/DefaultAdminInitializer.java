package dev.sorokin.eventmanager.config;

import dev.sorokin.eventmanager.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdminInitializer {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DefaultAdminInitializer(
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (userService.existsByLogin("admin")) {
            return;
        }
        userService.createAdmin(
                "admin",
                30,
                passwordEncoder.encode("admin")
        );
    }

}