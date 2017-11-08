package com.ml.spring.tuto.aae.controller;

import com.ml.spring.tuto.aae.persistance.model.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class RegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private static final String URI = "/register";

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping(URI)
    public ResponseEntity<Object> registerUserAccount(@Valid final UserDto user) {

        LOGGER.debug("Registering user account with information: {}", user);



        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }


}
