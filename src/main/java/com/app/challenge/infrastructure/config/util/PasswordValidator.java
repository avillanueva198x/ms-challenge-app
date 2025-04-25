package com.app.challenge.infrastructure.config.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final PasswordRegexProperties regexProps;
    private Pattern pattern;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.pattern = Pattern.compile(this.regexProps.getPassword());
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || !this.pattern.matcher(password).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.regexProps.getMessage())
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
