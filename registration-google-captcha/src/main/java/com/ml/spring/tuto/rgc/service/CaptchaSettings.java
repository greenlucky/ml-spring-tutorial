package com.ml.spring.tuto.rgc.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class CaptchaSettings {

    private String site;
    private String secret;

    public CaptchaSettings() {
    }

    public CaptchaSettings(String site, String secret) {
        this.site = site;
        this.secret = secret;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
