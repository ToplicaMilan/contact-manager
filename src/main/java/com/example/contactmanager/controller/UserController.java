package com.example.contactmanager.controller;

import com.example.contactmanager.controller.dto.ContactDto;
import com.example.contactmanager.controller.dto.UserRequestDto;
import com.example.contactmanager.controller.dto.UserResponseDto;
import com.example.contactmanager.controller.mapper.ContactMapper;
import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.domain.BridgeUser;
import com.example.contactmanager.domain.entity.ContactType;
import com.example.contactmanager.service.ContactService;
import com.example.contactmanager.service.ContactTypeService;
import com.example.contactmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ContactMapper contactMapper;
    private final ContactService contactService;
    private final ContactTypeService contactTypeService;

    public UserController(UserService userService, UserMapper userMapper,
                          ContactMapper contactMapper, ContactService contactService,
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
        var responseDto = userMapper.mapToDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactDto> createContact(@Valid @RequestBody ContactDto dto, @AuthenticationPrincipal BridgeUser bridgeUser) {

        var user = userService.findById(bridgeUser.getId());
        var contactType = contactTypeService.findById(1L);
        var contact = contactMapper.mapToEntity(dto);
        contact.setUser(user);
        contact.setContactType(contactType);
        contactService.saveContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
// lazy fetch, paginacija jpa cool metode za paginirano izvlacenje
    @GetMapping("/contact/{id}")
    public ResponseEntity<List<ContactDto>> getAllUserContacts(@PathVariable Long id) {
        var user = userService.findById(id);
        List<ContactDto> userContacts = new ArrayList<>();
        for (var contact : user.getContacts()) {
            userContacts.add(contactMapper.mapToDto(contact));
        }
        return ResponseEntity.ok(userContacts);
    }
}
