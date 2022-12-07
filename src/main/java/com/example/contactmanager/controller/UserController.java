package com.example.contactmanager.controller;

import com.example.contactmanager.controller.dto.UserCreationDto;
import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<UserCreationDto> signUp(@RequestBody UserCreationDto dto) {
        var user = userMapper.mapToEntity(dto);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
