package com.ml.spring.tuto.rgc.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ReCaptchaAttemptService {

    private final int MAX_ATTEMPT = 4;
    private LoadingCache<String, Integer> attemptsCache;

    public ReCaptchaAttemptService() {
        super();
        this.attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(4, TimeUnit.HOURS).build(
                new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(final String key) throws Exception {
                        return 0;
                    }
                }
        );
    }

    public void reCaptchaSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void reCaptchaFailed(String key) {
        int attempts = attemptsCache.getUnchecked(key);
        attempts++;
        attemptsCache.put(key, attempts);
    }


    public boolean isBlock(final String clientIP) {
        return attemptsCache.getUnchecked(clientIP) >= MAX_ATTEMPT;
    }
}
