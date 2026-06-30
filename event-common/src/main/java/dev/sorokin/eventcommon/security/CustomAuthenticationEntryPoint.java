package dev.sorokin.eventcommon.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sorokin.eventcommon.dto.ErrorMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        log.error("Handling authentication exception", authException);
        var messageResponse = new ErrorMessageResponse(
                "Failed to authenticate",
                authException.getMessage(),
                LocalDateTime.now()
        );

        var stringResponse = objectMapper.writeValueAsString(messageResponse);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(stringResponse);
    }

}