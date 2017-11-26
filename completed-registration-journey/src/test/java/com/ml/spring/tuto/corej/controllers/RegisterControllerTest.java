package com.ml.spring.tuto.corej.controllers;

import com.google.gson.Gson;
import com.ml.spring.tuto.corej.BaseTest;
import com.ml.spring.tuto.corej.persistence.model.PasswordDto;
import com.ml.spring.tuto.corej.persistence.model.User;
import com.ml.spring.tuto.corej.persistence.model.dto.UserDto;
import com.ml.spring.tuto.corej.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ContextConfiguration
public class RegisterControllerTest extends BaseTest{

    private final Logger LOGGER = LoggerFactory.getLogger(RegisterControllerTest.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private Gson gson;

    private MockMvc mvc;

    @Before
    public void init() {
        assertEquals(HttpStatus.OK, createUser().getStatusCode());

        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void testCreateNewUser() {

    }

    @Test
    public void testChangePassword() throws Exception {

        login();

        //change password
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("1234567");
        passwordDto.setNewPassword("1234567");
        passwordDto.setConfirmNewPassword("1234567");

        assertNotNull(passwordDto);

        LOGGER.info("PasswordDto: {}", passwordDto.toString());

        String url = "http://localhost:" + serverRandomPort + "/register/change-password";
        ResponseEntity<String> response = restTemplate.postForEntity(url, passwordDto, String.class);
        System.out.println(response.getStatusCode());
    }

    @Test
    //@WithMockUser(username = "nguyenlamit86@gmail.com", password = "123456", roles = "USER")
    public void testLogin() throws Exception {

        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setOldPassword("123456");
        passwordDto.setNewPassword("1234567");
        passwordDto.setConfirmNewPassword("1234567");

        mvc.perform(post("/register/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(passwordDto))
                .with(
                user("nguyenlamit86@gmail.com")
                        .password("123456").roles("USER")))
                .andExpect(authenticated());

    }

    private ResponseEntity<String> createUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("nguyenlamit86@gmail.com");
        userDto.setEmail("nguyenlamit86@gmail.com");
        userDto.setPassword("123456");
        userDto.setConfirmPassword("123456");

        String url = "http://localhost:" + serverRandomPort + "/register";
        return restTemplate.postForEntity(url, userDto, String.class);
    }

    private ResponseEntity<String> login() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("username", "nguyenlamit86@gmail.com");
        data.add("password", "123456");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);

        String urlLogin = "http://localhost:" + serverRandomPort + "/login";

        final ResponseEntity<String> response = restTemplate.postForEntity(urlLogin, request, String.class);

        System.out.println(response.getStatusCode());
        return response;
    }
}