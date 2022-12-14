package com.example.contactmanager.controller;

import com.example.contactmanager.controller.dto.ContactTypeDto;
import com.example.contactmanager.controller.dto.UserRequestDto;
import com.example.contactmanager.controller.dto.UserResponseDto;
import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.domain.entity.ContactType;
import com.example.contactmanager.service.exception.ConflictException;
import com.example.contactmanager.service.exception.ContactTypeException;
import com.example.contactmanager.service.ContactService;
import com.example.contactmanager.service.ContactTypeService;
import com.example.contactmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ContactTypeService contactTypeService;
    private final UserService userService;
    private final UserMapper userMapper;
    ContactService contactService;

    public AdminController(ContactTypeService typeService, UserService userService, UserMapper userMapper, ContactService contactService) {
        this.contactTypeService = typeService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.contactService = contactService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {
        var user = userMapper.mapToEntity(dto);
        userService.saveUser(user);
        var responseDto = userMapper.mapToDto(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(user.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@Validated(UserRequestDto.OnCreate.class) @RequestBody UserRequestDto dto,
                                                      @PathVariable Long id) {
        var oldUser = userService.findById(id);
        oldUser = userMapper.updateUser(oldUser, dto);
        userService.saveUser(oldUser);
        return ResponseEntity.ok(userMapper.mapToDto(oldUser));
    }

    @PostMapping("/contact-type")
    public ResponseEntity<ContactTypeDto> createContactType(@Validated(ContactTypeDto.OnCreate.class) @RequestBody ContactTypeDto dto) {
        if (contactTypeService.existsByType(dto.getType())) {
            throw new ContactTypeException("Contact Type already exists");
        }
        var newContactType = new ContactType();
        newContactType.setDescription(dto.getDescription());
        newContactType.setType(dto.getType());
        contactTypeService.saveContactType(newContactType);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(newContactType.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/contact-type/{id}")
    public ResponseEntity<ContactTypeDto> updateContactType(@PathVariable(name = "id") Long id,
                                                            @Validated(ContactTypeDto.OnUpdate.class)
                                                            @RequestBody ContactTypeDto dto) {
        var oldContactType = contactTypeService.findById(id);
        if (nonNull(dto.getDescription()) && !dto.getDescription().isEmpty()) {
            oldContactType.setDescription(dto.getDescription());
        }
        if (contactTypeService.existsByType(dto.getType())) {
            throw new ConflictException("Contact Type already exists");
        }
        oldContactType.setType(dto.getType());
        contactTypeService.saveContactType(oldContactType);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/contact-type/{id}")
    public ResponseEntity<Void> deleteContactType(@PathVariable(name = "id") Long id) {
        var oldContactType = contactTypeService.findById(id);
        contactTypeService.deleteContactType(oldContactType);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        var contact = contactService.findById(id);
        contactService.deleteContact(contact);
        return ResponseEntity.noContent().build();
    }
}
