package com.example.contactmanager.service;

import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.domain.repository.UserRepository;
import com.example.contactmanager.exception.UserEmailException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserEmailException("Email already taken");
        }
        userRepository.save(user);
    }

    public Optional<User> getUser(String email) {
        var user = userRepository.findByEmail(email);
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
