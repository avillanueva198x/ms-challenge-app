package com.app.challenge.infrastructure.config.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.regex")
public class PasswordRegexProperties {
    private String password;
    private String message;
}
