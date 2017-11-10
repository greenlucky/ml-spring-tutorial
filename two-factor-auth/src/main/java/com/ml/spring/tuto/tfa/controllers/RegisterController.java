package com.ml.spring.tuto.tfa.controllers;

import com.ml.spring.tuto.tfa.enums.RolesEnum;
import com.ml.spring.tuto.tfa.persistence.dto.UserDto;
import com.ml.spring.tuto.tfa.persistence.model.User;
import com.ml.spring.tuto.tfa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(RegisterController.URI)
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    public static final String URI = "/register";

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<Object> registerNewAccount(@RequestBody @Valid UserDto user, BindingResult result) {

        if(result.hasErrors())
            return new ResponseEntity<Object>(result.getAllErrors(), HttpStatus.EXPECTATION_FAILED);
        if(user.getRoles() == null || user.getRoles().isEmpty()) {
            Set<RolesEnum> roles = new HashSet<>();
            roles.add(RolesEnum.USER);
            user.setRoles(roles);
        }
        userService.createUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity getUserByEmail(@RequestParam("email") String email) {

        if(email == null)
            return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);

        final User user = userService.getByEmail(email);

        if(user == null)
            return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/generation-barcode")
    public ResponseEntity<Object> generationBarcode(@RequestParam("email") String email) {

        if(email == null)
            return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);

        final User user = userService.getByEmail(email);
        String qrBarcodeUrl = null;
        try {
            qrBarcodeUrl = userService.generateQRUrl(user);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Object>(qrBarcodeUrl, HttpStatus.OK);
    }

}
