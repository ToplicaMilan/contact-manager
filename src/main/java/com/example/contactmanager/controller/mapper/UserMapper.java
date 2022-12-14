package com.example.contactmanager.controller.mapper;

import com.example.contactmanager.controller.dto.UserRequestDto;
import com.example.contactmanager.controller.dto.UserResponseDto;
import com.example.contactmanager.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public User mapToEntity(UserRequestDto dto) {
        var user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return user;
    }

    public UserResponseDto mapToDto(User user) {
        var dto = new UserResponseDto();
        dto.setEmail(user.getEmail());
        dto.setRoleType(user.getRole());
        return dto;
    }

    public User updateUser(User user, UserRequestDto dto) {
        if (!dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }
        if (!dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (!dto.getRole().toString().isBlank()) {
            user.setRole(dto.getRole());
        }
        return user;
    }
}
