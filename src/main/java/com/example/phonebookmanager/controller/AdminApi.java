package com.example.phonebookmanager.controller;

import com.example.phonebookmanager.config.SwaggerConfig;
import com.example.phonebookmanager.controller.dto.ContactTypeDto;
import com.example.phonebookmanager.controller.dto.CustomPageDto;
import com.example.phonebookmanager.controller.dto.UserRequestDto;
import com.example.phonebookmanager.controller.dto.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {SwaggerConfig.ADMIN_TAG})
public interface AdminApi {

    @Operation(summary = "Creates user account.")
    @PostMapping("/user")
    ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto);

    @Operation(summary = "Updates user account.")
    @PutMapping("/user/{id}")
    ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto dto,
                                               @PathVariable Long id);

    @Operation(summary = "Retrieves specific user.")
    @GetMapping("/user/{id}")
    ResponseEntity<UserResponseDto> getUser(@PathVariable Long id);

    @Operation(summary = "Retrieves all users.")
    @GetMapping("/user")
    ResponseEntity<CustomPageDto> getAllUsers(Pageable pageable);

    @Operation(summary = "Creates contact type.")
    @PostMapping("/contact-type")
    ResponseEntity<ContactTypeDto> createContactType(@Validated(ContactTypeDto.OnCreate.class) @RequestBody ContactTypeDto dto);

    @Operation(summary = "Updates contact type info.")
    @PutMapping("/contact-type/{id}")
    ResponseEntity<ContactTypeDto> updateContactType(@Validated(ContactTypeDto.OnUpdate.class)
                                                     @RequestBody ContactTypeDto dto, @PathVariable(name = "id") Long id);

    @Operation(summary = "Deletes any contact type.")
    @DeleteMapping("/contact-type/{id}")
    ResponseEntity<Void> deleteContactType(@PathVariable(name = "id") Long id);

    @Operation(summary = "Deletes any contact.")
    @DeleteMapping("/contact/{id}")
    ResponseEntity<Void> deleteContact(@PathVariable Long id);
}
