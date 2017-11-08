package com.ml.spring.tuto.aae.validation.Impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.ml.spring.tuto.aae.persistance.model.dto.*;
import com.ml.spring.tuto.aae.validation.PasswordMatches;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(final Object value, ConstraintValidatorContext context) {
        final UserDto user = (UserDto) value;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
