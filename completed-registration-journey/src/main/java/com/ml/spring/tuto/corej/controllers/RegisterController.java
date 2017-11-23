package com.ml.spring.tuto.corej.controllers;

import com.ml.spring.tuto.corej.persistence.model.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(RegisterController.URI)
public class RegisterController {

    public static final String URI = "/register";

    @PostMapping
    public ResponseEntity<Object> registerNewUser(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<Object>(HttpStatus.OK);
    }


}
