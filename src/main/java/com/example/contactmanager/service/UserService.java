package com.example.contactmanager.service;

import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.domain.repository.UserRepository;
import com.example.contactmanager.exception.UserEmailException;
import com.example.contactmanager.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

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

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with this id does not exists"));

    }

//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
}
