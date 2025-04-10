package com.app.challenge.infrastructure.adapter.out.persistence;

import com.app.challenge.application.usecase.port.out.SaveUserPort;
import com.app.challenge.domain.model.User;
import com.app.challenge.infrastructure.adapter.out.persistence.mapper.UserEntityMapper;
import com.app.challenge.infrastructure.adapter.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort {

	private final UserRepository userRepository;
	private final UserEntityMapper mapper;

	@Override
	@Transactional
	public User save(User user) {
		var entity = mapper.toEntity(user);
		var saved = userRepository.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
}
