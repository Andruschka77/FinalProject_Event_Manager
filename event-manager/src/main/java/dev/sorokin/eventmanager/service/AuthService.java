package dev.sorokin.eventmanager.service;

import dev.sorokin.eventcommon.exception.ResourceNotFoundException;
import dev.sorokin.eventmanager.dto.request.UserCredentialsRequest;
import dev.sorokin.eventmanager.jwt.JwtTokenManager;
import dev.sorokin.eventmanager.model.domain.User;
import dev.sorokin.eventmanager.model.enums.UserRole;
import dev.sorokin.eventmanager.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtTokenManager jwtTokenManager,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenManager = jwtTokenManager;
        this.userRepository = userRepository;
    }

    public String authenticateUser(UserCredentialsRequest userCredentialsRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userCredentialsRequest.login(),
                        userCredentialsRequest.password()
                )
        );

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        Long userId = userRepository.findIdByLogin(userCredentialsRequest.login())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User", userCredentialsRequest.login())
                );

        return jwtTokenManager.generateToken(
                userCredentialsRequest.login(),
                UserRole.valueOf(role),
                userId
        );
    }

    public User getCurrentAuthenticatedUserOrThrow() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication not present");
        }
        return (User) authentication.getPrincipal();
    }

}