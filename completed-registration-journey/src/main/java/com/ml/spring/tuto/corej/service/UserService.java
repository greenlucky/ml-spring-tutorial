package com.ml.spring.tuto.corej.service;

import com.ml.spring.tuto.corej.persistence.model.User;
import com.ml.spring.tuto.corej.persistence.model.dto.UserDto;
import com.ml.spring.tuto.corej.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(UserDto userDto) {
        User user = new User(userDto);
        return userRepository.save(user);
    }
}
