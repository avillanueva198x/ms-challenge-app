package com.app.challenge.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable()) // Para permitir H2-console sin CSRF
				.headers(headers -> headers
						.frameOptions(frame -> frame.sameOrigin()) // <- ESTO PERMITE H2 EN IFRAMES
				)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								new AntPathRequestMatcher("/h2-console/**"),
								new AntPathRequestMatcher("/swagger-ui/**"),
								new AntPathRequestMatcher("/swagger-ui.html"),
								new AntPathRequestMatcher("/v3/api-docs/**"),
								new AntPathRequestMatcher("/api-docs/**"),
								new AntPathRequestMatcher("/")
						).permitAll()
						.anyRequest().permitAll() // Puedes restringir después si lo necesitas
				)
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}
}
