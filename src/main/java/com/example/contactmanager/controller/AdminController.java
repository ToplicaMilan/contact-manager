package com.example.contactmanager.controller;

import com.example.contactmanager.controller.dto.ContactTypeDto;
import com.example.contactmanager.controller.dto.UserCreationDto;
import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.domain.entity.ContactType;
import com.example.contactmanager.service.ContactTypeService;
import com.example.contactmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ContactTypeService contactTypeService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AdminController(ContactTypeService typeService, UserService userService, UserMapper userMapper) {
        this.contactTypeService = typeService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/user")
    public ResponseEntity<UserCreationDto> createUser(@Valid @RequestBody UserCreationDto dto) {
        var user = userMapper.dtoToEntity(dto);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/contact_type")
    public ResponseEntity<ContactTypeDto> createContactType(@Valid @RequestBody ContactTypeDto dto) {
        var newContactType = new ContactType();
        newContactType.setDescription(dto.getDescription());
        newContactType.setType(dto.getType());
        contactTypeService.saveContactType(newContactType);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/contact_type/{id}")
    public ResponseEntity<ContactTypeDto> updateContactType(@PathVariable(name = "id") Long id, @RequestBody ContactTypeDto dto) {
        var oldContactType = contactTypeService.findById(id);
        oldContactType.setType(dto.getType());
        oldContactType.setDescription(dto.getDescription());
        contactTypeService.saveContactType(oldContactType);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/contact_type/{id}")
    public ResponseEntity<ContactTypeDto> deleteContactType(@PathVariable(name = "id") Long id) {
        var oldContactType = contactTypeService.findById(id);
        contactTypeService.deleteContactType(oldContactType);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
