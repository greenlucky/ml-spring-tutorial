package com.ml.spring.tuto.rgc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private static final String URL = "/register";

    @GetMapping(URL)
    public String register(Model model) {

        return "register";
    }


}
