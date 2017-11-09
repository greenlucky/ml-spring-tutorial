package com.ml.spring.tuto.aae.controllers;

import com.ml.spring.tuto.aae.event.OnRegistrationCompleteEvent;
import com.ml.spring.tuto.aae.persistance.model.User;
import com.ml.spring.tuto.aae.persistance.model.VerificationToken;
import com.ml.spring.tuto.aae.persistance.model.dto.UserDto;
import com.ml.spring.tuto.aae.service.UserService;
import com.ml.spring.tuto.aae.service.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Null;


@RestController
public class RegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private static final String URI = "/v1/register";

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService tokenService;

    @PostMapping(URI)
    public ResponseEntity<Object> registerUserAccount(@RequestBody @Valid final UserDto user,
                                                      BindingResult result,
                                                      final HttpServletRequest request) {

        LOGGER.debug("Registering user account with information: {}", user.toString());

        if(result.hasErrors()) {
            LOGGER.error("Errors: {}", result.getAllErrors());
            return new ResponseEntity<Object>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            User registered = userService.createUserAccount(user);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl(request)));
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping(URI + "/confirm")
    public ResponseEntity<Object> registrationConfirm(@RequestParam(value = "token") String token)  {

        if(token.isEmpty() || token == null)
            return new ResponseEntity<Object>("Token is not null", HttpStatus.EXPECTATION_FAILED);

        final VerificationToken verToken = tokenService.getByToken(token);

        if(verToken == null)
            return new ResponseEntity<Object>("Token is not valid",HttpStatus.EXPECTATION_FAILED);
        /*if(verToken.isValidToken()) {
            User user = verToken.getUser();
            user.setEnabled(true);
            userService.updateUserAccount(user);
        }*/

        return new ResponseEntity<Object>(HttpStatus.OK);
    }
    private String appUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


}
