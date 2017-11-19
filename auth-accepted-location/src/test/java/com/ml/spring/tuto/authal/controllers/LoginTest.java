package com.ml.spring.tuto.authal.controllers;

import com.ml.spring.tuto.authal.BaseTest;
import com.ml.spring.tuto.authal.persistence.model.NewLocationToken;
import com.ml.spring.tuto.authal.persistence.model.UserLocation;
import com.ml.spring.tuto.authal.persistence.model.dto.UserDto;
import com.ml.spring.tuto.authal.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import static org.junit.Assert.*;

public class LoginTest extends BaseTest{

    @Autowired
    private UserService userService;

    @Test
    public void testLogin() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamit86@gmail.com");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");
        final String uri = "http://localhost:" + serverRandomPort + "/register";

        final ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);
        System.out.println(response.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<String, String>();
        data.add("username", "nguyenlamit86@gmail.com");
        data.add("password", "123456");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(data, headers);

        final String uriLogin = "http://localhost:" + serverRandomPort + "/login";

        final ResponseEntity<String> responseLogin = restTemplate.postForEntity(uriLogin, request, String.class);
        System.out.println(responseLogin.toString());
        Thread.sleep(10000);
        assertEquals(HttpStatus.MOVED_TEMPORARILY, responseLogin.getStatusCode());
    }

    @Test
    public void testEnabledNewLocation() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamit86@gmail.com");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");
        final String uri = "http://localhost:" + serverRandomPort + "/register";

        final ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);
        System.out.println(response.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<String, String>();
        data.add("username", "nguyenlamit86@gmail.com");
        data.add("password", "123456");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(data, headers);

        final String uriLogin = "http://localhost:" + serverRandomPort + "/login";

        final ResponseEntity<String> responseLogin = restTemplate.postForEntity(uriLogin, request, String.class);


        long tokenId = 1L;
        final NewLocationToken token = userService.getNewLocationToken(tokenId);

        assertNotNull(token);

        final String newLocUri = "http://localhost:" + serverRandomPort + "/register/enabled-new-loc?token=" + token.getToken();
        final ResponseEntity<String> responseLoc = restTemplate.getForEntity(newLocUri, String.class);

        assertEquals(HttpStatus.OK, responseLoc.getStatusCode());
    }


    @Test
    public void testEnabledNewLocationAndAddNewUserLocation() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setUsername("nguyenlamit86@gmail.com");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");
        final String uri = "http://localhost:" + serverRandomPort + "/register";

        final ResponseEntity<String> response = restTemplate.postForEntity(uri, userDto, String.class);
        System.out.println(response.toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<String, String>();
        data.add("username", "nguyenlamit86@gmail.com");
        data.add("password", "123456");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(data, headers);

        final String uriLogin = "http://localhost:" + serverRandomPort + "/login";

        final ResponseEntity<String> responseLogin = restTemplate.postForEntity(uriLogin, request, String.class);


        long tokenId = 1L;
        final NewLocationToken token = userService.getNewLocationToken(tokenId);

        assertNotNull(token);

        final String newLocUri = "http://localhost:" + serverRandomPort + "/register/enabled-new-loc?token=" + token.getToken();
        final ResponseEntity<String> responseLoc = restTemplate.getForEntity(newLocUri, String.class);

        assertEquals(HttpStatus.OK, responseLoc.getStatusCode());

        final NewLocationToken actualToken = userService.getNewLocationToken(tokenId);

        assertNull(actualToken);

        final UserLocation actualUserLoc = userService.getUserLocationByUserAndCountry(token.getUserLocation().getUser(), token.getUserLocation().getCountry());

        assertNotNull(actualUserLoc);
        assertEquals(token.getUserLocation().getCountry(), actualUserLoc.getCountry());
    }
}
