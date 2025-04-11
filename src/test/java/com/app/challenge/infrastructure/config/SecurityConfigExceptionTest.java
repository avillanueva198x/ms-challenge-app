package com.app.challenge.infrastructure.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


class SecurityConfigExceptionTest {

    @Test
    void shouldThrowChallengeHandleExceptionWhenHttpSecurityFails() {
        SecurityConfig config = new SecurityConfig();

        HttpSecurity httpSecurity = Mockito.mock(HttpSecurity.class);

        try {
            // Forzamos un fallo en el primer método encadenado
            Mockito.when(httpSecurity.csrf(Mockito.any())).thenThrow(new RuntimeException("Boom"));

            config.securityFilterChain(httpSecurity);
            Assertions.fail("Se esperaba ChallengeHandleException pero no fue lanzada");

        } catch (Exception ex) {
            Assertions.assertTrue(ex.getMessage().contains("Error configurando seguridad"));
        }
    }
}
