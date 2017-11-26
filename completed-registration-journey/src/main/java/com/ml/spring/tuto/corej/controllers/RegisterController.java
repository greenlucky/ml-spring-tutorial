package com.ml.spring.tuto.corej.controllers;

import com.google.gson.Gson;
import com.ml.spring.tuto.corej.enums.RolesEnum;
import com.ml.spring.tuto.corej.exceptions.InvalidOldPasswordException;
import com.ml.spring.tuto.corej.persistence.model.PasswordDto;
import com.ml.spring.tuto.corej.persistence.model.User;
import com.ml.spring.tuto.corej.persistence.model.dto.UserDto;
import com.ml.spring.tuto.corej.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(RegisterController.URI)
public class RegisterController {

    public static final String URI = "/register";

    @Autowired
    private UserService userService;

    @Autowired
    private Gson gson;

    @PostMapping
    public ResponseEntity<Object> registerNewUser(@RequestBody @Valid UserDto userDto) {

        if(userDto.getRoles() == null)
            userDto.setRole(RolesEnum.USER);

        userService.create(userDto);

        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getUserByEmail(@RequestParam("email") String email) {
        final User user = userService.getByEmail(email);
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePasswordUser(@RequestBody @Valid PasswordDto passwordDto) {

        final String email = gson.fromJson(gson.toJson(SecurityContextHolder.getContext().getAuthentication().getPrincipal()), User.class).getUsername();

        final User user = userService.getByEmail(email);

        if(!userService.passwordMatching(passwordDto.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException("Old password is not matching");
        }

        userService.changeUserPassword(user, passwordDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
