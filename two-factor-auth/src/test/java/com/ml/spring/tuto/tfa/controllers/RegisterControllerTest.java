package com.ml.spring.tuto.tfa.controllers;

import com.ml.spring.tuto.tfa.BaseTest;
import com.ml.spring.tuto.tfa.persistence.dto.UserDto;
import com.ml.spring.tuto.tfa.persistence.model.User;
import com.ml.spring.tuto.tfa.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class RegisterControllerTest extends BaseTest{

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterNewUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamit86");
        userDto.setPassword("1234567");
        userDto.setConfirmPassword("1234567");

        String uri = "http://localhost:" + serverRandomPort + "/register";

        final ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGenerateQRUrl() throws  Exception {
        User user = new User();
        user.setEmail("nguyenlamit86@gmail.com");
        user.setUsername("nguyenlamit86");
        user.setPassword("1234567");

        String QRUrl = userService.generateQRUrl(user);
        System.out.println(QRUrl);
    }

}