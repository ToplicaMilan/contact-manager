package com.example.phonebookmanager.controller.mapper;

import com.example.phonebookmanager.controller.dto.CustomPageDto;
import com.example.phonebookmanager.controller.dto.UserRequestDto;
import com.example.phonebookmanager.controller.dto.UserResponseDto;
import com.example.phonebookmanager.domain.entity.RoleType;
import com.example.phonebookmanager.domain.entity.User;
import com.example.phonebookmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

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
        if (dto.getRole().equals("USER")) {
            user.setRole(RoleType.USER);
        }
        if (dto.getRole().equals("ADMIN")) {
            user.setRole(RoleType.ADMIN);
        }
        return user;
    }

    public UserResponseDto mapToDto(User user) {
        var dto = new UserResponseDto();
        dto.setEmail(user.getEmail());
        dto.setRoleType(user.getRole());
        return dto;
    }

    public User updateUser(User user, UserRequestDto dto) {
        if (nonNull(dto.getEmail()) && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }
        if (nonNull(dto.getPassword()) && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (!dto.getRole().isBlank()) {
            if (dto.getRole().equals("USER")) {
                user.setRole(RoleType.USER);
            }
            if (dto.getRole().equals("ADMIN")) {
                user.setRole(RoleType.ADMIN);
            }
        }
        return user;
    }

    public CustomPageDto mapToPageDto(Pageable pageable) {
        var users = userService.getAllUsers(pageable).map(this::mapToDto);
        return new CustomPageDto<>(users.getContent(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                users.getTotalElements());
    }
}
