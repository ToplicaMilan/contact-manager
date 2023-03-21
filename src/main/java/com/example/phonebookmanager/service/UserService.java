package com.example.phonebookmanager.service;

import com.example.phonebookmanager.domain.entity.User;
import com.example.phonebookmanager.domain.repository.UserRepository;
import com.example.phonebookmanager.service.exception.ConflictException;
import com.example.phonebookmanager.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email already taken");
        }
        userRepository.save(user);
        emailService.sendCreateConfirmation(user.getEmail());
    }

    public void updateUser(User updatedUser, String oldEmail) {
        if (!oldEmail.equals(updatedUser.getEmail()) && userRepository.existsByEmail(updatedUser.getEmail())) {
            throw new ConflictException("Email already taken");
        }
        userRepository.save(updatedUser);
        emailService.sendUpdateConfirmation(updatedUser.getEmail());
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
