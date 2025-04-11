package com.app.challenge.infrastructure.adapter.persistence.entity.user;

import com.app.challenge.infrastructure.adapter.persistence.entity.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "t_user_phones")
@Getter
@Setter
@NoArgsConstructor
public class PhoneEntity extends BaseModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 15)
    private String number;

    @Column(nullable = false, length = 10)
    private String citycode;

    @Column(name = "country_code", nullable = false, length = 10)
    private String countrycode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_phone"))
    private UserEntity user;

}
