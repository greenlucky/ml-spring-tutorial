package com.ml.spring.tuto.tfa.controllers;

import com.ml.spring.tuto.tfa.persistence.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @GetMapping("login-success")
    public String loginSuccess() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hi " + user.getUsername();
    }
}
