package com.app.challenge.application.mapper;

import com.app.challenge.domain.model.dto.Phone;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.domain.model.dto.request.CreateUserRequest;
import com.app.challenge.domain.model.dto.request.PhoneRequest;
import com.app.challenge.domain.model.dto.response.UserResponse;
import org.springframework.stereotype.Component;

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
            .build();
    }

    public UserResponse toResponse(User user) {
        List<PhoneRequest> phones = user.getPhones().stream()
            .map(p -> new PhoneRequest(p.getNumber(), p.getCityCode(), p.getCountryCode()))
            .toList();

        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getToken(),
            user.getCreated(),
            user.getModified(),
            user.getLastLogin(),
            user.isActive(),
            phones
        );
    }
}
