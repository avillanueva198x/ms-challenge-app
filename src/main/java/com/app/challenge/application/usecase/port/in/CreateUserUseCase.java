package com.app.challenge.application.usecase.port.in;

import com.app.challenge.domain.model.User;

public interface CreateUserUseCase {
	User createUser(User user);
}
