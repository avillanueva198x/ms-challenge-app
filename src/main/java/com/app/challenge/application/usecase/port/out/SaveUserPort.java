package com.app.challenge.application.usecase.port.out;

import com.app.challenge.domain.model.User;

public interface SaveUserPort {
	User save(User user);
	boolean existsByEmail(String email);

}
