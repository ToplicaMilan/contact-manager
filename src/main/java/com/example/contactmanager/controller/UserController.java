package com.example.contactmanager.controller;

import com.example.contactmanager.controller.dto.ContactDto;
import com.example.contactmanager.controller.dto.CustomPageDto;
import com.example.contactmanager.controller.dto.UserRequestDto;
import com.example.contactmanager.controller.dto.UserResponseDto;
import com.example.contactmanager.controller.mapper.ContactMapper;
import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.domain.BridgeUser;
import com.example.contactmanager.service.ContactService;
import com.example.contactmanager.service.ContactTypeService;
import com.example.contactmanager.service.UserService;
import com.example.contactmanager.service.exception.ForbiddenException;
import com.example.contactmanager.service.exception.UnauthorizedException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserController implements UserApi {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ContactMapper contactMapper;
    private final ContactService contactService;
    private final ContactTypeService contactTypeService;

    public UserController(UserService userService, UserMapper userMapper, ContactMapper contactMapper, ContactService contactService,
                          ContactTypeService contactTypeService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.contactMapper = contactMapper;
        this.contactService = contactService;
        this.contactTypeService = contactTypeService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto dto) {
        var user = userMapper.mapToEntity(dto);
        userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(user.getId());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/contact")
    public ResponseEntity<Void> createContact(@Validated(ContactDto.OnCreate.class) @RequestBody ContactDto dto,
                                              @AuthenticationPrincipal BridgeUser bridgeUser) {
        var user = userService.findById(bridgeUser.getId());
        var contactType = contactTypeService.findByType(dto.getType());
        var contact = contactMapper.mapToEntity(dto);
        contact.setUser(user);
        contact.setContactType(contactType);
        contactService.saveContact(contact);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(contact.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<ContactDto> updateContact(@Validated(ContactDto.OnUpdate.class) @RequestBody ContactDto dto, @PathVariable Long id) {
        var oldContact = contactService.findById(id);
        oldContact = contactMapper.updateContact(oldContact, dto);
        if (!dto.getType().isEmpty()) {
            var contactType = contactTypeService.findByType(dto.getType());
            oldContact.setContactType(contactType);
        }
        contactService.saveContact(oldContact);
        return ResponseEntity.ok(contactMapper.mapToDto(oldContact));
    }

    @GetMapping("/contact")
    public ResponseEntity<CustomPageDto<ContactDto>> getAllContacts(@AuthenticationPrincipal BridgeUser bridgeUser, Pageable pageable) {
        var user = userService.findById(bridgeUser.getId());
        return ResponseEntity.ok(contactMapper.mapToPageDto(contactService.getAllUserContacts(user, pageable)));
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<ContactDto> getContact(@AuthenticationPrincipal BridgeUser bridgeUser, @PathVariable Long id) {
        var contact = contactService.findById(id);
        if (!(bridgeUser.getId().equals(contact.getUser().getId()))) {
            throw new UnauthorizedException("Unauthorized access");
        }
        return ResponseEntity.ok(contactMapper.mapToDto(contactService.getUserContacts(id)));
    }

    @GetMapping("/contact/search")
    public ResponseEntity<CustomPageDto<ContactDto>> searchContacts(@AuthenticationPrincipal BridgeUser bridgeUser,
                                                                    @RequestParam(name = "param", required = false) String param, Pageable pageable) {
        var user = userService.findById(bridgeUser.getId());
        return ResponseEntity.ok(contactMapper.mapToPageDto(contactService.searchUserContacts(user, param, pageable)));
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<Void> deleteContact(@AuthenticationPrincipal BridgeUser bridgeUser, @PathVariable Long id) {
        var contact = contactService.findById(id);
        if (bridgeUser.getId().equals(contact.getUser().getId())) {
            contactService.deleteContact(contact);
        } else {
            throw new ForbiddenException("Forbidden Access");
        }
        return ResponseEntity.noContent().build();
    }
}


