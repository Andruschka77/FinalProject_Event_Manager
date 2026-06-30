package dev.sorokin.eventmanager.jwt;

import dev.sorokin.eventcommon.exception.ResourceNotFoundException;
import dev.sorokin.eventmanager.mapper.UserEntityMapper;
import dev.sorokin.eventmanager.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public JwtTokenFilter(
            JwtTokenManager jwtTokenManager,
            UserRepository userRepository,
            UserEntityMapper userEntityMapper
    ) {
        this.jwtTokenManager = jwtTokenManager;
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwtToken = authorizationHeader.substring(7);

        String loginFromToken;
        try {
            loginFromToken = jwtTokenManager.getLoginFromToken(jwtToken);
        } catch (Exception e) {
            log.error("Error while reading jwt", e);
            filterChain.doFilter(request, response);
            return;
        }

        var userEntity = userRepository.findByLogin(loginFromToken)
                .orElseThrow(() -> new ResourceNotFoundException("User", loginFromToken));

        var authorities = List.of(
                new SimpleGrantedAuthority(
                        jwtTokenManager.getRoleFromToken(jwtToken)
                )
        );

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userEntityMapper.toDomain(userEntity),
                null,
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }

}