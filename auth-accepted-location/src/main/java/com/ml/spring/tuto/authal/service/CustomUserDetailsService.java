package com.ml.spring.tuto.authal.service;

import com.ml.spring.tuto.authal.persistence.model.User;
import com.ml.spring.tuto.authal.persistence.repositoris.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService{

    private final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.info("User is coming: {}", email);
        final User user = userService.getByEmail(email);
        LOGGER.info("User info: {}", user);

        if(user != null) return user;
        return null;
    }
}
