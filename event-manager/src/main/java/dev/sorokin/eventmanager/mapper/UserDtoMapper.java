package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.dto.response.UserResponse;
import dev.sorokin.eventmanager.model.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserResponse toDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getAge(),
                user.getRole()
        );
    }

}