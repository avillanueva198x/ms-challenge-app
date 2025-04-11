package com.app.challenge.infrastructure.adapter.persistence;

import com.app.challenge.domain.port.SaveUserPort;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.application.mapper.UserEntityMapper;
import com.app.challenge.infrastructure.adapter.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort {

    private final UserRepository userRepository;
    private final UserEntityMapper mapper;

    @Override
    @Transactional
    public User save(User user) {
        var entity = this.mapper.toEntity(user);
        var saved = this.userRepository.save(entity);
        return this.mapper.toDomain(saved);
    }

    @Override
    public boolean existsByEmail(String email) {
        log.debug("Consulta si existe un usuario con el email: {}", email);
        return this.userRepository.findByEmail(email).isPresent();
    }

}
