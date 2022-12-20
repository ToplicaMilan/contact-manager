package com.example.contactmanager.controller.mapper;

import com.example.contactmanager.controller.dto.CustomPageDto;
import com.example.contactmanager.controller.dto.UserRequestDto;
import com.example.contactmanager.controller.dto.UserResponseDto;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserService userService;

    public UserMapper(UserService userService) {
        this.userService = userService;
    }

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

    public CustomPageDto mapToPageDto(Pageable pageable) {
        var users = userService.getAllUsers(pageable).map(this::mapToDto);
        return new CustomPageDto<>(users.getContent(), pageable.getPageNumber(), pageable.getPageSize(), users.getTotalElements());
    }
}
