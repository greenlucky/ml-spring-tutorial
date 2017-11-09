package com.ml.spring.tuto.aae.service;

import com.ml.spring.tuto.aae.event.OnRegistrationCompleteEvent;
import com.ml.spring.tuto.aae.persistance.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class RegistrationListenerService implements ApplicationListener<OnRegistrationCompleteEvent>{

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationListenerService.class);

    @Autowired
    private UserService service;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        CompletableFuture.supplyAsync(() -> this.confirmRegistration(event));
    }

    private Object confirmRegistration(OnRegistrationCompleteEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerification(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmUrl = event.getAppUrl() + "/register/confirm?token=" + token;
        String message = "Hi " + user.getUsername() + "</br>";
        message += "This is email active your account on our website. Please lick below link to active. rn" + confirmUrl;


        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);

        LOGGER.info("Sent mail to {} with content: {}", user.getEmail(), message);

        return null;
    }
}
