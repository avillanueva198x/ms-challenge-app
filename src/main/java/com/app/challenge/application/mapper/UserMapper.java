package com.app.challenge.application.mapper;

import com.app.challenge.domain.model.dto.Phone;
import com.app.challenge.domain.model.dto.User;
import com.app.challenge.domain.model.dto.request.CreateUserRequest;
import com.app.challenge.domain.model.dto.request.PhoneRequest;
import com.app.challenge.domain.model.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toDomain(CreateUserRequest request) {
        List<Phone> phones = request.getPhones().stream()
            .map(p -> new Phone(p.getNumber(), p.getCitycode(), p.getContrycode()))
            .collect(Collectors.toList());

        return User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .phones(phones)
            .isActive(true)
            .build();
    }


    public UserResponse toResponse(User user) {
        List<PhoneRequest> phones = user.getPhones().stream()
            .map(p -> new PhoneRequest(p.getNumber(), p.getCityCode(), p.getCountryCode()))
            .collect(Collectors.toList());

        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getToken(),
            user.getCreated(),
            user.getModified(),
            user.getLastLogin(),
            phones,
            user.isActive()
        );
    }
}
