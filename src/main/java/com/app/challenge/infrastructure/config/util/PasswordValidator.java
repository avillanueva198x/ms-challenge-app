package com.app.challenge.infrastructure.config.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private Pattern pattern;
    private String message;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        PasswordRegexProperties regexProps = PasswordPropertiesHolder.get();
        if (regexProps == null) {
            throw new IllegalStateException("Password regex properties not initialized!");
        }
        this.pattern = Pattern.compile(regexProps.getPassword());
        this.message = regexProps.getMessage();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || !pattern.matcher(password).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
