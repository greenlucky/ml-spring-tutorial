package com.ml.spring.tuto.authal.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping
    public ResponseEntity<Object> index() {
        return new ResponseEntity<Object>("Hi, I'm index", HttpStatus.OK);
    }
}
