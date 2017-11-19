package com.ml.spring.tuto.authal.persistence.model;

import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnDifferentLocationLoginEvent extends ApplicationEvent {

    private final Locale locale;
    private final String username;
    private final String ipAddress;
    private final NewLocationToken token;
    private final String appUrl;

    public OnDifferentLocationLoginEvent(Locale locale, String username, String ipAddress, NewLocationToken token, String appUrl) {
        super(token);
        this.locale = locale;
        this.username = username;
        this.ipAddress = ipAddress;
        this.token = token;
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getUsername() {
        return username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public NewLocationToken getToken() {
        return token;
    }

    public String getAppUrl() {
        return appUrl;
    }
}
