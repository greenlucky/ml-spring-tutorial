package com.ml.spring.tuto.rgc.controllers;

import com.ml.spring.tuto.rgc.persistence.dto.UserDto;
import com.ml.spring.tuto.rgc.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class RegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private static final String URL = "/register";

    @Autowired
    private CaptchaService captchaService;

    @GetMapping(URL)
    public String register(Model model) {

        return "register";
    }

    @PostMapping(URL)
    public String register(@Valid UserDto user, HttpServletRequest request) {
        String response = request.getParameter("g-recaptcha-response");
        captchaService.processResponse(response);

        return "register";
    }


}
