package dev.sorokin.eventnotificator.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public Long getIdCurrentAuthenticatedUserOrThrow() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication not present");
        }
        return (Long) authentication.getPrincipal();
    }

}