package com.example.contactmanager.controller;

import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.dto.UserCreation;
import com.example.contactmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE})
        public ResponseEntity<UserDetails> signUp(@RequestBody UserCreation dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping(value = "/admin", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDetails> admin(@RequestBody String email) {
        String ivan = email;
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUser(email));
    }
}
