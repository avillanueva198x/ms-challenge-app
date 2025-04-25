package com.app.challenge.infrastructure.rest;

import com.app.challenge.application.handler.CreateUserHandler;
import com.app.challenge.domain.model.dto.request.CreateUserRequest;
import com.app.challenge.domain.model.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UserController {

    private final CreateUserHandler createUserHandler;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = this.createUserHandler.handle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
