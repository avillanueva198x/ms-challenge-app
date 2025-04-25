package com.app.challenge.infrastructure.config.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "La contraseña no cumple con el formato requerido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
