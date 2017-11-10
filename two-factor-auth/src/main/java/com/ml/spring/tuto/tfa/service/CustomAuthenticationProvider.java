package com.ml.spring.tuto.tfa.service;

import com.ml.spring.tuto.tfa.persistence.model.User;
import com.ml.spring.tuto.tfa.security.CustomWebAuthenticationDetails;
import org.jboss.aerogear.security.otp.Totp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        String verificationCode = ((CustomWebAuthenticationDetails) auth.getDetails()).getVerificationCode();

        Authentication result = super.authenticate(auth);
        LOGGER.info("Credentials: {}", result.getCredentials());
        LOGGER.info("Authorities: {}", result.getAuthorities());

        User user = userService.getByEmail(auth.getName());

        if (user.isUsing2FA()) {
            Totp totp = new Totp(user.getSecret());
            if (!isValidLong(verificationCode) || !totp.verify(verificationCode)) {
                throw new BadCredentialsException("Invalid verification code");
            }
        }


        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> authetication) {
        return authetication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
