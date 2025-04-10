package com.app.challenge.infrastructure.adapter.in.rest.handler;

import com.app.challenge.application.usecase.port.in.CreateUserUseCase;
import com.app.challenge.infrastructure.adapter.in.rest.dto.CreateUserRequest;
import com.app.challenge.infrastructure.adapter.in.rest.dto.UserResponse;
import com.app.challenge.infrastructure.adapter.in.rest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserHandler {

    private final CreateUserUseCase createUserUseCase;
    private final UserMapper userMapper;

    public UserResponse handle(CreateUserRequest request) {
        var user = userMapper.toDomain(request);
        var created = createUserUseCase.createUser(user);
        return userMapper.toResponse(created);
    }

}
