package com.app.challenge.infrastructure.adapter;

import com.app.challenge.application.mapper.UserEntityMapper;
import com.app.challenge.domain.model.dto.Phone;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.infrastructure.adapter.persistence.UserPersistenceAdapter;
import com.app.challenge.infrastructure.adapter.persistence.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({UserPersistenceAdapter.class, UserEntityMapper.class}) // Sin MapStruct, es manual
class UserPersistenceAdapterTest {

    @Autowired
    private UserPersistenceAdapter adapter;

    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("Debe guardar y retornar correctamente un usuario")
    void shouldSaveUserSuccessfully() {
        // Arrange
        var user = new User(
            UUID.randomUUID(),
            "Juan",
            "juan@mail.com",
            "HunterApp2",
            List.of(new Phone("1234567", "1", "57")),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "jwt-token-fake",
            true
        );

        // Act
        var savedUser = this.adapter.save(user);

        // Assert
        assertNotNull(savedUser.getId());
        Assertions.assertEquals("juan@mail.com", savedUser.getEmail());
        Assertions.assertEquals(1, savedUser.getPhones().size());
        assertTrue(this.repository.findByEmail("juan@mail.com").isPresent());
    }

    @Test
    @DisplayName("Debe retornar verdadero si el email existe en la base de datos")
    void shouldReturnTrueIfEmailExists() {
        // Arrange: primero persistimos uno
        var user = new User(
            UUID.randomUUID(),
            "Maria",
            "maria@mail.com",
            "HunterApp2",
            List.of(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "jwt-token-maria",
            true
        );
        this.adapter.save(user);

        // Act
        boolean exists = this.adapter.existsByEmail("maria@mail.com");

        // Assert
        assertTrue(exists);
    }

    @Test
    @DisplayName("Debe retornar falso si el email no existe")
    void shouldReturnFalseIfEmailDoesNotExist() {
        boolean exists = this.adapter.existsByEmail("noexiste@mail.com");
        assertFalse(exists);
    }
}
