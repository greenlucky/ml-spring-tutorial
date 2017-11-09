package com.ml.spring.tuto.aae.service;

import com.ml.spring.tuto.aae.BaseTest;
import com.ml.spring.tuto.aae.persistance.model.User;
import com.ml.spring.tuto.aae.persistance.model.VerificationToken;
import com.ml.spring.tuto.aae.persistance.model.dto.UserDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.Assert.*;

public class UserServiceTest extends BaseTest{

    @Autowired
    private VerificationTokenService tokenService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateNewTokenForUser() throws Exception {
        final UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamit86");
        userDto.setPassword("123456");
        User user = userService.createUserAccount(userDto);
        String token = UUID.randomUUID().toString();
        userService.createVerification(user, token);

        VerificationToken verToken = tokenService.getById(1L);
        assertEquals(token, verToken.getToken());
    }

}