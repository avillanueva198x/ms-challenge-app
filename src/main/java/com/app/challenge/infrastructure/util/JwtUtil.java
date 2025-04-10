package com.app.challenge.infrastructure.util;

import com.app.challenge.domain.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Genera un JWT con los claims básicos para el usuario.
     *
     * @param user Usuario del cual se genera el token
     * @return token JWT válido por 1 hora
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000); // 1 hora

        return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("name", user.getName())
            .setIssuedAt(now)
            .setExpiration(exp)
            .signWith(key)
            .compact();
    }
}
