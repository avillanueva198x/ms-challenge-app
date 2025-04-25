package com.app.challenge.infrastructure.adapter.persistence.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class BaseModel {

    private LocalDateTime created;

    private LocalDateTime modified;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

}
