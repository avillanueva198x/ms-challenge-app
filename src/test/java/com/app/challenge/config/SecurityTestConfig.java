package com.app.challenge.config;

import com.app.challenge.infrastructure.rest.advice.ChallengeHandleException;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
        } catch (Exception e) {
            Assertions.fail("Error configurando seguridad ", e);
            throw new ChallengeHandleException("Error configurando seguridad", e);
        }
    }
}
