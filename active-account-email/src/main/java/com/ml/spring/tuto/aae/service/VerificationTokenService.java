package com.ml.spring.tuto.aae.service;

import com.ml.spring.tuto.aae.persistance.model.VerificationToken;
import com.ml.spring.tuto.aae.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public VerificationToken getById(long id) {
        return tokenRepository.findOne(id);
    }

    public VerificationToken getByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
