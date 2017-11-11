package com.ml.spring.tuto.rgc.service;

import com.ml.spring.tuto.rgc.exceptions.InvalidReCaptchaException;
import com.ml.spring.tuto.rgc.persistence.model.GoogleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.regex.Pattern;

@Service
public class CaptchaService {

    @Autowired
    private CaptchaSettings captchaSettings;

    @Autowired
    private ReCaptchaAttemptService reCaptchaAttemptService;


    @Autowired
    private RestTemplate restTemplate;

    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");


    public void processResponse(final String response) {

        if (reCaptchaAttemptService.isBlock(getClientIP())) {
            throw new InvalidReCaptchaException("Client exceeded maximum number of failed attempts");
        }

        if (!responseSanityCheck(response)) {
            throw new InvalidReCaptchaException("Response contains invalid characters");
        }

        URI verifyUri = URI.create(String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                captchaSettings.getSecret(), response, getClientIP()));

        final GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        if (!googleResponse.isSuccess()) {
            if (googleResponse.hasClientError()) {
                reCaptchaAttemptService.reCaptchaFailed(getClientIP());
            }
            throw new InvalidReCaptchaException("reCaptcha was not successfully validated");
        }

        reCaptchaAttemptService.reCaptchaSucceeded(getClientIP());
    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }

    private String getClientIP() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof NativeWebRequest) {
            HttpServletRequest request = (HttpServletRequest) ((NativeWebRequest) attribs).getNativeRequest();
            return request.getRemoteAddr();
        }
        return "0.0.0.1";
    }
}
