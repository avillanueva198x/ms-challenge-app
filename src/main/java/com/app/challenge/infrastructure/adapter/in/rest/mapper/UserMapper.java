package com.app.challenge.infrastructure.adapter.in.rest.mapper;

import com.app.challenge.domain.model.Phone;
import com.app.challenge.domain.model.User;
import com.app.challenge.infrastructure.adapter.in.rest.dto.CreateUserRequest;
import com.app.challenge.infrastructure.adapter.in.rest.dto.PhoneRequest;
import com.app.challenge.infrastructure.adapter.in.rest.dto.UserResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserMapper {

	public User toDomain(CreateUserRequest request) {
		List<Phone> phones = request.phones().stream()
				.map(p -> new Phone(p.number(), p.citycode(), p.contrycode()))
				.toList();

		return User.builder()
				.name(request.name())
				.email(request.email())
				.password(request.password())
				.phones(phones)
				.isActive(true)
				.created(LocalDateTime.now())
				.build();
	}

	public UserResponse toResponse(User user) {
		List<PhoneRequest> phones = user.getPhones().stream()
				.map(p -> new PhoneRequest(p.getNumber(), p.getCityCode(), p.getCountryCode()))
				.toList();

		return new UserResponse(
				user.getId(),
				user.getCreated(),
				user.getModified(),
				user.getLastLogin(),
				user.getToken(),
				user.isActive(),
				user.getName(),
				user.getEmail(),
				phones
		);
	}
}
