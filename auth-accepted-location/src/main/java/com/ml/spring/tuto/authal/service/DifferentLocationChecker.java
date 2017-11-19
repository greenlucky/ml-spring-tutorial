package com.ml.spring.tuto.authal.service;

import com.ml.spring.tuto.authal.exception.UnusualLocationException;
import com.ml.spring.tuto.authal.persistence.model.NewLocationToken;
import com.ml.spring.tuto.authal.persistence.model.OnDifferentLocationLoginEvent;
import com.ml.spring.tuto.authal.persistence.model.UserLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class DifferentLocationChecker implements UserDetailsChecker {

    @Autowired
    private String currentClientIPAddress;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void check(UserDetails userDetails) {
        final String ipAddress = currentClientIPAddress;
        final NewLocationToken token = userService.isNewLocation(userDetails.getUsername(), ipAddress);
        if(token != null) {
            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            eventPublisher.publishEvent(new OnDifferentLocationLoginEvent(request.getLocale(), userDetails.getUsername(), ipAddress, token, appUrl));
            throw new UnusualLocationException("Unusual location");
        }
    }
}
