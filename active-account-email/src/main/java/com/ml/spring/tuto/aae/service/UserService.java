package com.ml.spring.tuto.aae.service;

import com.ml.spring.tuto.aae.persistance.model.User;
import com.ml.spring.tuto.aae.persistance.model.VerificationToken;
import com.ml.spring.tuto.aae.persistance.model.dto.UserDto;
import com.ml.spring.tuto.aae.repository.UserRepository;
import com.ml.spring.tuto.aae.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public User createUserAccount(final UserDto userDto) {
        User user = new User(userDto);
        return userRepository.save(user);
    }


    public void createVerification(final User user, final String token) {
        final VerificationToken verToken = new VerificationToken(token, user);
        tokenRepository.save(verToken);
    }

    public void updateUserAccount(User user) {

    }
}
