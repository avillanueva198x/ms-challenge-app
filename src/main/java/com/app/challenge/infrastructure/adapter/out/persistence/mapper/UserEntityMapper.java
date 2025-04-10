package com.app.challenge.infrastructure.adapter.out.persistence.mapper;

import com.app.challenge.domain.model.Phone;
import com.app.challenge.domain.model.User;
import com.app.challenge.infrastructure.adapter.out.persistence.entity.PhoneEntity;
import com.app.challenge.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setToken(user.getToken());
        entity.setActive(user.isActive());
        entity.setCreated(user.getCreated());
        entity.setModified(user.getModified());
        entity.setLastLogin(user.getLastLogin());

        List<PhoneEntity> phones = user.getPhones().stream().map(p -> {
            PhoneEntity pe = new PhoneEntity();
            pe.setNumber(p.getNumber());
            pe.setCitycode(p.getCityCode());
            pe.setContrycode(p.getCountryCode());
            pe.setUser(entity);
            return pe;
        }).toList();

        entity.setPhones(phones);
        return entity;
    }

    public User toDomain(UserEntity entity) {
        List<Phone> phones = entity.getPhones().stream()
            .map(p -> new Phone(p.getNumber(), p.getCitycode(), p.getContrycode()))
            .toList();

        return new User(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getPassword(),
            phones,
            entity.getCreated(),
            entity.getModified(),
            entity.getLastLogin(),
            entity.getToken(),
            entity.isActive()
        );
    }
}
