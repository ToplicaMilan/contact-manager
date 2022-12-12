package com.example.contactmanager.controller;

import com.example.contactmanager.controller.dto.ContactDto;
import com.example.contactmanager.controller.dto.CustomPageDto;
import com.example.contactmanager.controller.dto.UserRequestDto;
import com.example.contactmanager.controller.dto.UserResponseDto;
import com.example.contactmanager.controller.mapper.ContactMapper;
import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.domain.BridgeUser;
import com.example.contactmanager.domain.repository.ContactRepository;
import com.example.contactmanager.domain.repository.UserRepository;
import com.example.contactmanager.service.ContactService;
import com.example.contactmanager.service.ContactTypeService;
import com.example.contactmanager.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ContactMapper contactMapper;
    private final ContactService contactService;
    private final ContactRepository contactRepository;
    private final ContactTypeService contactTypeService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserMapper userMapper, ContactMapper contactMapper, ContactService contactService, ContactTypeService contactTypeService, ContactRepository contactRepository, UserRepository userRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.contactMapper = contactMapper;
        this.contactService = contactService;
        this.contactTypeService = contactTypeService;
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto dto) {
        var user = userMapper.mapToEntity(dto);
        userService.saveUser(user);
        var responseDto = userMapper.mapToDto(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(user.getId());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactDto> createContact(@Valid @RequestBody ContactDto dto, @AuthenticationPrincipal BridgeUser bridgeUser) {
        var user = userService.findById(bridgeUser.getId());
        var contactType = contactTypeService.findByType(dto.getType());
        var contact = contactMapper.mapToEntity(dto);
        contact.setUser(user);
        contact.setContactType(contactType);
        contactService.saveContact(contact);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(contact.getId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<Void> deleteContact(@AuthenticationPrincipal BridgeUser bridgeUser, @PathVariable Long id) {
        var user = userService.findById(bridgeUser.getId());
        var contact = contactService.findById(id);
        Long userId = contact.getUser().getId();
        if (bridgeUser.getId().equals(contact.getUser().getId())) {
            contactService.deleteContact(contact);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/contact")
    public ResponseEntity<CustomPageDto> getAllContacts(@AuthenticationPrincipal BridgeUser bridgeUser, Pageable pageable) {
        var user = userService.findById(bridgeUser.getId());
        return ResponseEntity.ok(contactMapper.mapToPageDto(user, pageable));
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<Void> updateContact(@PathVariable Long id, @RequestBody ContactDto dto) {
        var contact = contactService.findById(id);
        contactMapper.updateContact(contact, dto);
        if (!dto.getType().isEmpty()) {
            var contactType = contactTypeService.findByType(dto.getType());
            contact.setContactType(contactType);
        }
        contactService.saveContact(contact);
        return ResponseEntity.noContent().build();
    }
}
