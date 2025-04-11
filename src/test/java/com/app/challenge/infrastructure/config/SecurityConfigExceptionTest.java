package com.app.challenge.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class SecurityConfigExceptionTest {

    @Test
    void shouldThrowChallengeHandleExceptionWhenHttpSecurityFails() {
        SecurityConfig config = new SecurityConfig();

        HttpSecurity httpSecurity = mock(HttpSecurity.class);

        try {
            // Forzamos un fallo en el primer método encadenado
            when(httpSecurity.csrf(any())).thenThrow(new RuntimeException("Boom"));

            config.securityFilterChain(httpSecurity);
            fail("Se esperaba ChallengeHandleException pero no fue lanzada");

        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Error configurando seguridad"));
        }
    }
}
