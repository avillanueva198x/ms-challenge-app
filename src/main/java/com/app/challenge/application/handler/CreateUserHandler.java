package com.app.challenge.application.handler;

import com.app.challenge.application.mapper.UserMapper;
import com.app.challenge.application.service.CreateUserService;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.domain.model.dto.request.CreateUserRequest;
import com.app.challenge.domain.model.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserHandler {

    private final CreateUserService createUserService;
    private final UserMapper userMapper;

    public UserResponse handle(CreateUserRequest request) {
        User user = this.userMapper.toDomain(request);
        User created = this.createUserService.createUser(user);
        return this.userMapper.toResponse(created);
    }

}
