package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.dto.request.UserRegistrationRequest;
import dev.sorokin.eventmanager.model.domain.User;
import dev.sorokin.eventmanager.model.entity.UserEntity;
import dev.sorokin.eventmanager.model.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public User toDomain(UserEntity user) {
        return new User(
                user.getId(),
                user.getLogin(),
                user.getAge(),
                user.getRole()
        );
    }

    public UserEntity toEntity(UserRegistrationRequest user, String hashedPass) {
        return new UserEntity(
                user.login(),
                user.age(),
                hashedPass,
                UserRole.USER
        );
    }

}