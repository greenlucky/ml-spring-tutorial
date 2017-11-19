package com.ml.spring.tuto.authal.controllers;

import com.ml.spring.tuto.authal.BaseTest;
import com.ml.spring.tuto.authal.persistence.model.dto.UserDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class RegisterControllerTest extends BaseTest{

    @Test
    public void testRegisterNewUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamit86");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");
        String uri = "http://localhost:" + serverRandomPort + "/register";

        ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);
        System.out.println(response.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}