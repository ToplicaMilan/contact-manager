package com.example.contactmanager.controller;

import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.controller.dto.UserCreationDto;
import com.example.contactmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping(value = "/signup")
//        public ResponseEntity<UserDetails> signUp(@RequestBody UserCreation dto) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
//    }

    @PostMapping(value = "/signup")
    public ResponseEntity<UserCreationDto> signUp(@RequestBody UserCreationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping(value = "/admin")
    public ResponseEntity<Optional<User>> admin(@RequestBody String email) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUser(email));
    }

    @GetMapping(value = "/admin/find")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.findAll());
    }
}
