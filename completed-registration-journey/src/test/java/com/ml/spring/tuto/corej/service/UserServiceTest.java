package com.ml.spring.tuto.corej.service;

import com.ml.spring.tuto.corej.BaseTest;
import com.ml.spring.tuto.corej.persistence.model.dto.UserDto;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.*;


public class UserServiceTest extends BaseTest{

    @Autowired
    private UserService userService;

    private Validator validator;

    @Before
    public void inti() throws Exception {
        validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(
                        new ResourceBundleMessageInterpolator(
                                new PlatformResourceBundleLocator("ValidatorMessages")
                        )
                )
                .buildValidatorFactory()
                .getValidator();
    }

    @Test
    public void testCreateUser() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUsername("nguyenlamit86@gmail.com");
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        constraintViolations.forEach(msg -> System.out.println(msg.getMessage()));

        assertNotNull(constraintViolations);

        userService.create(userDto);
    }

}