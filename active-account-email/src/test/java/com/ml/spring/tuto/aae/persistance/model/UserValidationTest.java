package com.ml.spring.tuto.aae.persistance.model;

import com.ml.spring.tuto.aae.persistance.model.dto.UserDto;
import com.ml.spring.tuto.aae.validation.PasswordMatches;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserValidationTest {

    private UserDto userDto;

    private Validator validator;

    @Before
    public void init() throws Exception {

        validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(
                        new ResourceBundleMessageInterpolator(
                                new PlatformResourceBundleLocator("ValidationMessages")
                        )
                ).buildValidatorFactory().getValidator();

        userDto = new UserDto();
    }

    @Test
    public void testAnnotationPasswordMatches() throws Exception {
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123457");
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto);

        constraintViolations.forEach(msg -> System.out.println(msg.getMessage()));

    }

}
