package dev.sorokin.eventmanager.service;

import dev.sorokin.eventmanager.dto.request.UserCredentialsRequest;
import dev.sorokin.eventmanager.jwt.JwtTokenManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtTokenManager jwtTokenManager
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenManager = jwtTokenManager;
    }

    public String authenticateUser(UserCredentialsRequest userCredentialsRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userCredentialsRequest.login(),
                        userCredentialsRequest.password()
                )
        );
        return jwtTokenManager.generateToken(userCredentialsRequest.login());
    }

}