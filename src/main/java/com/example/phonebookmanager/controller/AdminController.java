package com.example.phonebookmanager.controller;

import com.example.phonebookmanager.controller.dto.ContactTypeDto;
import com.example.phonebookmanager.controller.dto.CustomPageDto;
import com.example.phonebookmanager.controller.dto.UserRequestDto;
import com.example.phonebookmanager.controller.dto.UserResponseDto;
import com.example.phonebookmanager.controller.mapper.UserMapper;
import com.example.phonebookmanager.domain.entity.ContactType;
import com.example.phonebookmanager.service.ContactService;
import com.example.phonebookmanager.service.ContactTypeService;
import com.example.phonebookmanager.service.UserService;
import com.example.phonebookmanager.service.exception.ConflictException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/admin")
public class AdminController implements AdminApi {

    private final ContactTypeService contactTypeService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ContactService contactService;

    public AdminController(ContactTypeService typeService, UserService userService, UserMapper userMapper,
                           ContactService contactService) {
        this.contactTypeService = typeService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.contactService = contactService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@Validated(UserRequestDto.OnCreate.class)
                                                          @RequestBody UserRequestDto dto) {
        var user = userMapper.mapToEntity(dto);
        userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(user.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@Validated(UserRequestDto.OnUpdate.class)
                                                          @RequestBody UserRequestDto dto, @PathVariable Long id) {
        var oldUser = userService.findById(id);
        String oldEmail = oldUser.getEmail();
        var updatedUser = userMapper.updateUser(oldUser, dto);
        userService.updateUser(updatedUser, oldEmail);
        return ResponseEntity.ok(userMapper.mapToDto(oldUser));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.mapToDto(userService.getUser(id)));
    }

    @GetMapping("/user")
    public ResponseEntity<CustomPageDto> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userMapper.mapToPageDto(pageable));
    }

    @PostMapping("/contact-type")
    public ResponseEntity<ContactTypeDto> createContactType(@Validated(ContactTypeDto.OnCreate.class) @RequestBody ContactTypeDto dto) {
        if (contactTypeService.existsByType(dto.getType())) {
            throw new ConflictException("Contact Type already exists");
        }
        var newContactType = new ContactType();
        newContactType.setDescription(dto.getDescription());
        newContactType.setType(dto.getType());
        contactTypeService.saveContactType(newContactType);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(newContactType.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/contact-type/{id}")
    public ResponseEntity<ContactTypeDto> updateContactType(@Validated(ContactTypeDto.OnUpdate.class)
                                                                @RequestBody ContactTypeDto dto, @PathVariable(name = "id") Long id) {
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
