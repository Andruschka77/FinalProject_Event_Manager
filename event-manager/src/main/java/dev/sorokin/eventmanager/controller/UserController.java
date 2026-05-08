package dev.sorokin.eventmanager.controller;

import dev.sorokin.eventmanager.dto.request.UserCredentialsRequest;
import dev.sorokin.eventmanager.dto.request.UserRegistrationRequest;
import dev.sorokin.eventmanager.dto.response.JwtTokenResponse;
import dev.sorokin.eventmanager.dto.response.UserResponse;
import dev.sorokin.eventmanager.mapper.JwtDtoMapper;
import dev.sorokin.eventmanager.mapper.UserDtoMapper;
import dev.sorokin.eventmanager.model.domain.User;
import dev.sorokin.eventmanager.service.AuthService;
import dev.sorokin.eventmanager.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final AuthService authService;
    private final UserDtoMapper userDtoMapper;
    private final JwtDtoMapper jwtDtoMapper;

    public UserController(
            UserService userService,
            AuthService authService,
            UserDtoMapper userDtoMapper,
            JwtDtoMapper jwtDtoMapper
    ) {
        this.userService = userService;
        this.authService = authService;
        this.userDtoMapper = userDtoMapper;
        this.jwtDtoMapper = jwtDtoMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(
            @RequestBody @Valid UserRegistrationRequest userRegistrationRequest
    ) {
        log.info("Get request for sign-up: login={}", userRegistrationRequest.login());

        User registerUser = userService.registerUser(userRegistrationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDtoMapper.toDto(registerUser));
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtTokenResponse> authenticateUser(
            @RequestBody @Valid UserCredentialsRequest userCredentialsRequest
    ) {
        log.info("Get request for sign-in: login={}", userCredentialsRequest.login());

        var token = authService.authenticateUser(userCredentialsRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jwtDtoMapper.toDto(token));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable("userId") Long userId
    ) {
        log.info("Get request for find user by id: userId={}", userId);

        var foundUser = userService.findById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDtoMapper.toDto(foundUser));
    }

}