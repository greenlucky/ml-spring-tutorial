package com.ml.spring.tuto.authal.controllers;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.ml.spring.tuto.authal.enums.RolesEnum;
import com.ml.spring.tuto.authal.persistence.model.NewLocationToken;
import com.ml.spring.tuto.authal.persistence.model.User;
import com.ml.spring.tuto.authal.persistence.model.dto.UserDto;
import com.ml.spring.tuto.authal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(RegisterController.URI)
public class RegisterController {

    public static final String URI = "/register";

    @Autowired
    private UserService userService;

    @Autowired
    private String clientIPAddress;

    @PostMapping
    public ResponseEntity<Object> registerNewUser(@RequestBody @Valid UserDto userDto) {

        final User user = userService.create(userDto);
        clientIPAddress = "70.32.89.160";
        if (user == null) return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

        try {
            userService.addUserLocation(user, clientIPAddress);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/enabled-new-loc")
    public ResponseEntity<Object> enabledNewLoc(@RequestParam("token") String token) {

        if (token == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        NewLocationToken loc = userService.isValidNewLocationToken(token);

        if (loc == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getUserByEmail(@RequestParam("email") String email) {
        if (email == null) return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        final User user = userService.getByEmail(email);
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }
}
