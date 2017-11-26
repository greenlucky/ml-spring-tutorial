package com.ml.spring.tuto.corej.service;

import com.ml.spring.tuto.corej.persistence.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        if(email == null)
            throw new UsernameNotFoundException("Username is null");

        LOGGER.info("Someone is coming: {}", email);
        final User user = userService.getByEmail(email);
        if (user == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        LOGGER.info("User info: {}", user.toString());

        return user;
    }
}
