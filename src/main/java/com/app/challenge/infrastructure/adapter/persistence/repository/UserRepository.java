package com.app.challenge.infrastructure.adapter.persistence.repository;

import com.app.challenge.infrastructure.adapter.persistence.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
}
