package com.example.contactmanager.controller;

import com.example.contactmanager.controller.dto.ContactDto;
import com.example.contactmanager.controller.dto.UserRequestDto;
import com.example.contactmanager.controller.dto.UserResponseDto;
import com.example.contactmanager.controller.mapper.ContactMapper;
import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.domain.BridgeUser;
import com.example.contactmanager.domain.repository.ContactRepository;
import com.example.contactmanager.service.ContactService;
import com.example.contactmanager.service.ContactTypeService;
import com.example.contactmanager.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ContactMapper contactMapper;
    private final ContactService contactService;
    private final ContactRepository contactRepository;
    private final ContactTypeService contactTypeService;

    public UserController(UserService userService, UserMapper userMapper,
                          ContactMapper contactMapper, ContactService contactService,
                          ContactTypeService contactTypeService, ContactRepository contactRepository)
    {
        this.userService = userService;
        this.userMapper = userMapper;
        this.contactMapper = contactMapper;
        this.contactService = contactService;
        this.contactTypeService = contactTypeService;
        this.contactRepository = contactRepository;
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
    public ResponseEntity<ContactDto> createContact(
            @Valid @RequestBody ContactDto dto,
            @AuthenticationPrincipal BridgeUser bridgeUser)
    {
        var user = userService.findById(bridgeUser.getId());
        var contactType = contactTypeService.findByType(dto.getType());
        var contact = contactMapper.mapToEntity(dto);
        contact.setUser(user);
        contact.setContactType(contactType);
        contactService.saveContact(contact);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(contact.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/contact")
    public ResponseEntity<List<ContactDto>> getAllUserContacts(
            @AuthenticationPrincipal BridgeUser bridgeUser,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "4") Integer pageSize)
    {
        var user = userService.findById(bridgeUser.getId());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<ContactDto> userContacts = new ArrayList<>();

        for (var contact : contactRepository.findAllByUserId(user.getId(), pageRequest)) {
            userContacts.add(contactMapper.mapToDto(contact));
        }
        return ResponseEntity.ok(userContacts);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<Void> deleteUserContact(@PathVariable Long id) {
        var contact = contactService.findById(id);
        contactService.deleteContact(contact);
        return ResponseEntity.noContent().build();
    }
}
