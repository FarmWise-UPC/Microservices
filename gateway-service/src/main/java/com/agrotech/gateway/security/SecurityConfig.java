package com.agrotech.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/api/v1/authentication/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/iam/swagger-ui/**",
                                "/iam/v3/api-docs",
                                "/appointments/swagger-ui/**",
                                "/appointments/v3/api-docs",
                                "/management/swagger-ui/**",
                                "/management/v3/api-docs",
                                "/posts/swagger-ui/**",
                                "/posts/v3/api-docs",
                                "/profiles/swagger-ui/**",
                                "/profiles/v3/api-docs"
                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }
}