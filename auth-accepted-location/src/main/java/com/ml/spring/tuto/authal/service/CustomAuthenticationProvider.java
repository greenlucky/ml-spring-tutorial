package com.ml.spring.tuto.authal.service;

import com.ml.spring.tuto.authal.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        final User user = userService.getByEmail(auth.getName());
        if ((user == null)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        final Authentication result = super.authenticate(auth);

        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }
}
