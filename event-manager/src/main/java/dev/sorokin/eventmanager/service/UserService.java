package dev.sorokin.eventmanager.service;

import dev.sorokin.eventmanager.dto.request.UserRegistrationRequest;
import dev.sorokin.eventcommon.exception.ResourceNotFoundException;
import dev.sorokin.eventmanager.mapper.UserEntityMapper;
import dev.sorokin.eventmanager.model.domain.User;
import dev.sorokin.eventmanager.model.entity.UserEntity;
import dev.sorokin.eventmanager.model.enums.UserRole;
import dev.sorokin.eventmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityMapper userEntityMapper;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserEntityMapper userEntityMapper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEntityMapper = userEntityMapper;
    }

    public User registerUser(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByLogin(userRegistrationRequest.login())) {
            throw new IllegalArgumentException("Имя пользователя уже занято");
        }
        String hashedPass = passwordEncoder.encode(userRegistrationRequest.password());
        var userToSave = userEntityMapper.toEntity(userRegistrationRequest, hashedPass);

        return userEntityMapper.toDomain(
                userRepository.save(userToSave)
        );
    }

    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public void createAdmin(String login, Integer age, String password) {
        UserEntity admin = new UserEntity(
                login,
                age,
                password,
                UserRole.ADMIN
        );
        userRepository.save(admin);
    }

    public User findById(Long userId) {
        var foundUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", userId)
                );
        return userEntityMapper.toDomain(foundUser);
    }

}