package com.app.challenge.infrastructure.adapter.in.rest;

import com.app.challenge.infrastructure.adapter.in.rest.dto.CreateUserRequest;
import com.app.challenge.infrastructure.adapter.in.rest.dto.UserResponse;
import com.app.challenge.infrastructure.adapter.in.rest.handler.CreateUserHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UserController {

	private final CreateUserHandler createUserHandler;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
		var response = createUserHandler.handle(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
