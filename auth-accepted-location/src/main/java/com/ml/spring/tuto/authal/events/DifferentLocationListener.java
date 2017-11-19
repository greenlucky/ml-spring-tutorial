package com.ml.spring.tuto.authal.events;

import com.ml.spring.tuto.authal.persistence.model.OnDifferentLocationLoginEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DifferentLocationListener implements ApplicationListener<OnDifferentLocationLoginEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(OnDifferentLocationLoginEvent event) {

        String subject = "Login attempt from different location";
        String recipientAddress = event.getUsername();
        String from = env.getProperty("support.email");
        String message = String.format(
                "Your account has been login from different location in %s at %s.",
                new Date().toString(),
                event.getToken().getUserLocation().getCountry());

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(recipientAddress);
        mail.setSubject(subject);
        mail.setFrom(from);
        mail.setText(message);
        mailSender.send(mail);
    }
}
