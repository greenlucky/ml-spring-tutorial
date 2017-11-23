package com.ml.spring.tuto.corej.validation;

import com.ml.spring.tuto.corej.persistence.model.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PassMatching, Object> {

    @Override
    public void initialize(PassMatching constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        final UserDto user = (UserDto) value;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
