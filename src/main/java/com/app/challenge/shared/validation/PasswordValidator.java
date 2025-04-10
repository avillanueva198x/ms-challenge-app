package com.app.challenge.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

	private final Pattern pattern;
	private final String message;

	public PasswordValidator(
			@Value("${app.regex.password}") String passwordRegex,
			@Value("${app.regex.message}") String message) {
		this.pattern = Pattern.compile(passwordRegex);
		this.message = message;
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if (password == null || !pattern.matcher(password).matches()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}
		return true;
	}
}
