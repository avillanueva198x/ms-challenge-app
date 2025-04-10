package com.app.challenge.application.usecase;

import com.app.challenge.application.usecase.port.in.CreateUserUseCase;
import com.app.challenge.application.usecase.port.out.SaveUserPort;
import com.app.challenge.domain.model.User;
import com.app.challenge.infrastructure.util.JwtUtil;
import com.app.challenge.shared.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

	private final SaveUserPort saveUserPort;
	private final JwtUtil jwtUtil;

	@Override
	public User createUser(User user) {
		if (saveUserPort.existsByEmail(user.getEmail())) {
			throw new EmailAlreadyExistsException("El correo ya registrado");
		}

		LocalDateTime now = LocalDateTime.now();
		user.setId(UUID.randomUUID());
		/*user.setCreated(now);
		user.setModified(now);
		user.setLastLogin(now);*/
		user.setToken(jwtUtil.generateToken(user));
		user.setActive(true);

		return saveUserPort.save(user);
	}
}
