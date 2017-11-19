package com.ml.spring.tuto.authal.validation.Impl;


import com.ml.spring.tuto.authal.persistence.model.dto.UserDto;
import com.ml.spring.tuto.authal.validation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(final Object obj, ConstraintValidatorContext context) {
        final UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
