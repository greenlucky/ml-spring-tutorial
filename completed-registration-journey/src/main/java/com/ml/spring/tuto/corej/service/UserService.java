package com.ml.spring.tuto.corej.service;

import com.ml.spring.tuto.corej.persistence.model.User;
import com.ml.spring.tuto.corej.persistence.model.dto.UserDto;
import com.ml.spring.tuto.corej.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = new User(userDto);
        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean passwordMatching(String oldPassword, String encodedPassword) {
        return passwordEncoder.matches(oldPassword, encodedPassword);
    }

    public void changeUserPassword(final User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
