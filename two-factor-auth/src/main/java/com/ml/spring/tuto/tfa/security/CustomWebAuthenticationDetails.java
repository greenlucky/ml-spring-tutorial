package com.ml.spring.tuto.tfa.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private String verificationCode;

    public CustomWebAuthenticationDetails(HttpServletRequest context) {
        super(context);
        verificationCode = context.getParameter("code");
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
