package com.app.challenge.infrastructure.config.util;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PasswordPropertiesHolder {

    private static PasswordRegexProperties regexProperties;

    private final PasswordRegexProperties injectedProps;

    public PasswordPropertiesHolder(PasswordRegexProperties injectedProps) {
        this.injectedProps = injectedProps;
    }

    @PostConstruct
    public void init() {
        this.regexProperties = injectedProps;
    }

    public static PasswordRegexProperties get() {
        return regexProperties;
    }

}
