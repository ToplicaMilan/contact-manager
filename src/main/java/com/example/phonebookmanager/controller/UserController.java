package com.example.phonebookmanager.controller;

import com.example.phonebookmanager.controller.dto.ContactDto;
import com.example.phonebookmanager.controller.dto.CustomPageDto;
import com.example.phonebookmanager.controller.mapper.ContactMapper;
import com.example.phonebookmanager.domain.BridgeUser;
import com.example.phonebookmanager.service.ContactService;
import com.example.phonebookmanager.service.ContactTypeService;
import com.example.phonebookmanager.service.UserService;
import com.example.phonebookmanager.service.exception.ForbiddenException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/user")
public class UserController implements UserApi {

    private final UserService userService;
    private final ContactMapper contactMapper;
    private final ContactService contactService;
    private final ContactTypeService contactTypeService;

    public UserController(UserService userService, ContactMapper contactMapper, ContactService contactService,
                          ContactTypeService contactTypeService) {
        this.userService = userService;
        this.contactMapper = contactMapper;
        this.contactService = contactService;
        this.contactTypeService = contactTypeService;
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
    public ResponseEntity<ContactDto> updateContact(@Validated(ContactDto.OnUpdate.class) @RequestBody ContactDto dto, @PathVariable Long id,
                                                    @AuthenticationPrincipal BridgeUser bridgeUser) {
        var oldContact = contactService.findById(id);
        if (!(bridgeUser.getId().equals(oldContact.getUser().getId()))) {
            throw new ForbiddenException("Forbidden Access");
        }
        var updatedContact = contactMapper.updateContact(oldContact, dto);
        if (nonNull(dto.getType()) && !dto.getType().isEmpty()) {
            if (contactTypeService.existsByType(dto.getType())) {
                var contactType = contactTypeService.findByType(dto.getType());
                updatedContact.setContactType(contactType);
            }
        }
        contactService.saveContact(updatedContact);
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
            throw new ForbiddenException("Forbidden Access");
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
        if (!bridgeUser.getId().equals(contact.getUser().getId())) {
            throw new ForbiddenException("Forbidden Access");
        } else {
            contactService.deleteContact(contact);
        }
        return ResponseEntity.noContent().build();
    }
}


