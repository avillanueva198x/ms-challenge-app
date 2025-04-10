package com.app.challenge.application.usecase;

import com.app.challenge.application.usecase.port.in.CreateUserUseCase;
import com.app.challenge.application.usecase.port.out.SaveUserPort;
import com.app.challenge.domain.model.User;
import com.app.challenge.infrastructure.util.JwtUtil;
import com.app.challenge.shared.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

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
