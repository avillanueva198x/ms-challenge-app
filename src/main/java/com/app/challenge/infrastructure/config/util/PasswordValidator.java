package com.app.challenge.infrastructure.config.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final Environment environment;
    private Pattern pattern;
    private String message;

    public PasswordValidator(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        PasswordRegexProperties regexProps = PasswordPropertiesHolder.get();
        if (regexProps != null) {
            this.pattern = Pattern.compile(regexProps.getPassword());
            this.message = regexProps.getMessage();
        }else{
            String regex = environment.getProperty("app.regex.password");
            if (regex == null) {
                throw new IllegalStateException("No se encontró la propiedad 'app.regex.password'.");
            }
            this.pattern = Pattern.compile(regex);
        }
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
