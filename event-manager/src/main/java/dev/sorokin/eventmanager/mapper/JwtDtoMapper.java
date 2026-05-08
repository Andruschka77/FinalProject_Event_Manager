package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.dto.response.JwtTokenResponse;
import org.springframework.stereotype.Component;

@Component
public class JwtDtoMapper {

    public JwtTokenResponse toDto(String token) {
        return new JwtTokenResponse(token);
    }

}