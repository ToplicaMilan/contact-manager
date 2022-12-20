package com.example.contactmanager.service;

import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.domain.repository.UserRepository;
import com.example.contactmanager.service.exception.ConflictException;
import com.example.contactmanager.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email already taken");
        }
        userRepository.save(user);
    }

    public void updateUser(User updatedUser, String oldEmail) {
        if (!oldEmail.equals(updatedUser.getEmail()) && userRepository.existsByEmail(updatedUser.getEmail())) {
            throw new ConflictException("Email already taken");
        }
        userRepository.save(updatedUser);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
