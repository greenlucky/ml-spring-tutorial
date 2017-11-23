package com.ml.spring.tuto.corej.controllers;

import com.ml.spring.tuto.corej.BaseTest;
import com.ml.spring.tuto.corej.persistence.model.dto.UserDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class RegisterControllerTest extends BaseTest{

    @Test
    public void testCreateNewUser() {

        UserDto userDto = new UserDto();
        userDto.setUsername("nguyenlamit86@gmail.com");
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");

        String url = "http://localhost:" + serverRandomPort + "/register";
        ResponseEntity<String> response = restTemplate.postForEntity(url, userDto, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}