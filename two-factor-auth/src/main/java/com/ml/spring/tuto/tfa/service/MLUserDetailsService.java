package com.ml.spring.tuto.tfa.service;

import com.ml.spring.tuto.tfa.persistence.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service(value = "userDetailsService")
@Transactional
public class MLUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MLUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        LOGGER.info("Incoming request login by email: {}", email);
        final User user = userService.getByEmail(email);
        LOGGER.info("User has been login: {}", user.toString());
        if (user != null)
            return user;
        return null;
    }
}
