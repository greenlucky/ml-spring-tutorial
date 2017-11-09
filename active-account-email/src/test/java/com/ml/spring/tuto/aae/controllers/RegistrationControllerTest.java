package com.ml.spring.tuto.aae.controllers;

import com.ml.spring.tuto.aae.BaseTest;
import com.ml.spring.tuto.aae.persistance.model.User;
import com.ml.spring.tuto.aae.persistance.model.VerificationToken;
import com.ml.spring.tuto.aae.persistance.model.dto.UserDto;
import com.ml.spring.tuto.aae.service.UserService;
import com.ml.spring.tuto.aae.service.VerificationTokenService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;


public class RegistrationControllerTest extends BaseTest{

    @Autowired
    private VerificationTokenService tokenService;

    @Autowired
    private UserService userService;

    @Before
    public void init() throws Exception {
    }

    @Test
    public void testRegisterUserAccountPasswortMatching() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamagg");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");

        String uri = "http://localhost:" + randomPortServer + "/v1/register";
        ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testRegisterUserAccountPasswordNotMatching() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamagg");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123457");

        String uri = "http://localhost:" + randomPortServer + "/v1/register";
        ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        System.out.println(response.getBody());
    }

    @Test
    public void testConfirmRegistrationToken() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamagg");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");

        String uri = "http://localhost:" + randomPortServer + "/v1/register";
        final ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        final VerificationToken token = tokenService.getById(1L);

        uri = "http://localhost:" + randomPortServer + "/v1/register/confirm?token=" + token.getToken();
        final ResponseEntity<String> responseVer = restTemplate.getForEntity(uri, String.class);

        assertEquals(responseVer.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testConfirmRegistrationTokenIsValidAndEnablingUserAccount() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamagg");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");

        String uri = "http://localhost:" + randomPortServer + "/v1/register";
        final ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        final VerificationToken token = tokenService.getById(1L);

        uri = "http://localhost:" + randomPortServer + "/v1/register/confirm?token=" + token.getToken();
        final ResponseEntity<String> responseVer = restTemplate.getForEntity(uri, String.class);

        assertEquals(responseVer.getStatusCode(), HttpStatus.OK);

        User actualUser = userService.getByUsername(userDto.getUsername());

        assertEquals(actualUser.isEnabled(), true);
    }

    @Test
    public void testReGeneralRegistrationToken() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamagg");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");

        String uri = "http://localhost:" + randomPortServer + "/v1/register";
        final ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Thread.sleep(10000);

        final VerificationToken token = tokenService.getById(1L);

        uri = "http://localhost:" + randomPortServer + "/v1/register/resend-token?token=" + token.getToken();
        final ResponseEntity<String> responseRes = restTemplate.getForEntity(uri, String.class);

        assertEquals(responseRes.getStatusCode(), HttpStatus.OK);

        Thread.sleep(10000);
        final VerificationToken newToken = tokenService.getById(2L);

        uri = "http://localhost:" + randomPortServer + "/v1/register/confirm?token=" + newToken.getToken();
        final ResponseEntity<String> newresponseVer = restTemplate.getForEntity(uri, String.class);

        assertEquals(newresponseVer.getStatusCode(), HttpStatus.OK);

        User actualUser = userService.getByUsername(userDto.getUsername());

        assertEquals(actualUser.isEnabled(), true);
    }
}