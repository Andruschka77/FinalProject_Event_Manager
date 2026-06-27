package dev.sorokin.eventnotificator.jwt;

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

    public JwtTokenFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
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

        Long userIdFromToken;
        try {
            userIdFromToken = jwtTokenManager.getUserIdFromToken(jwtToken);
        } catch (Exception e) {
            log.error("Error while reading jwt", e);
            filterChain.doFilter(request, response);
            return;
        }

        var authorities = List.of(
                new SimpleGrantedAuthority(
                        jwtTokenManager.getRoleFromToken(jwtToken)
                )
        );

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userIdFromToken,
                null,
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }

}