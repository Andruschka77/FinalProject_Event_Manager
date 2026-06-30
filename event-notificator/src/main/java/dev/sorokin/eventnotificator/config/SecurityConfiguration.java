package dev.sorokin.eventnotificator.config;

import dev.sorokin.eventcommon.security.CustomAccessDeniedHandler;
import dev.sorokin.eventcommon.security.CustomAuthenticationEntryPoint;
import dev.sorokin.eventnotificator.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfiguration(
            CustomAccessDeniedHandler customAccessDeniedHandler,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            JwtTokenFilter jwtTokenFilter
    ) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/event-notificator-openapi.yaml",
                                    "/v3/api-docs/**",
                                    "/swagger-resources/**",
                                    "/webjars/**"
                            ).permitAll()
                            .requestMatchers("/notifications").hasAnyAuthority("USER", "ADMIN")
                            .anyRequest().authenticated()
                )
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(jwtTokenFilter, AnonymousAuthenticationFilter.class)
                .build();
    }

}