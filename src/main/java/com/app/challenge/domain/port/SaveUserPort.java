package com.app.challenge.domain.port;

import com.app.challenge.domain.model.dto.User;

public interface SaveUserPort {
    User save(User user);

    boolean existsByEmail(String email);

}
