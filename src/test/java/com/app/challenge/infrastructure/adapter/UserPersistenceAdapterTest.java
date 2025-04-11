package com.app.challenge.infrastructure.adapter;

import com.app.challenge.application.mapper.UserEntityMapper;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.domain.port.SaveUserPort;
import com.app.challenge.infrastructure.adapter.persistence.UserPersistenceAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({UserPersistenceAdapter.class, UserEntityMapper.class})
class UserPersistenceAdapterTest {

    @Autowired
    private SaveUserPort adapter;

    @Test
    @DisplayName("Debe guardar un usuario y verificar que existe por email")
    void shouldSaveAndRetrieveUser() {
        // Arrange
        var user = new User(
            UUID.randomUUID(),
            "Juan Pérez",
            "correo@mail.com",
            "Password123",
            List.of(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "fake-jwt-token",
            true
        );

        // Act
        User saved = adapter.save(user);

        // Assert
        assertNotNull(saved.getId());
        assertTrue(adapter.existsByEmail("correo@mail.com"));
    }
}
