package com.example.contactmanager.controller;

import com.example.contactmanager.controller.dto.ContactTypeDto;
import com.example.contactmanager.controller.dto.UserCreationDto;
import com.example.contactmanager.service.ContactTypeService;
import com.example.contactmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ContactTypeService typeService;
    private final UserService userService;

    public AdminController(ContactTypeService typeService, UserService userService) {
        this.typeService = typeService;
        this.userService = userService;
    }

    @PostMapping("/contact_type")
    public ResponseEntity<String> createContactType(@Valid @RequestBody ContactTypeDto dto) {
        typeService.createContactType(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto.getType());
    }

    @PostMapping("/user")
    public ResponseEntity<String> createContactType(@Valid @RequestBody UserCreationDto dto) {
        userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto.getEmail());
    }
}
