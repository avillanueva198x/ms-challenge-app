package com.app.challenge.domain.usecase;

import com.app.challenge.application.service.CreateUserService;
import com.app.challenge.domain.port.SaveUserPort;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.infrastructure.config.util.JwtUtil;
import com.app.challenge.infrastructure.rest.advice.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserService {

    private final SaveUserPort saveUserPort;
    private final JwtUtil jwtUtil;

    @Override
    public User createUser(User user) {
        if (this.saveUserPort.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("El correo ya registrado");
        }

        user.setId(UUID.randomUUID());
        user.setToken(this.jwtUtil.generateToken(user));
        user.setActive(true);

        return this.saveUserPort.save(user);
    }
}
