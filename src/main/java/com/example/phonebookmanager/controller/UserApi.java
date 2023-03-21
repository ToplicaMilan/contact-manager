package com.example.phonebookmanager.controller;

import com.example.phonebookmanager.config.SwaggerConfig;
import com.example.phonebookmanager.controller.dto.ContactDto;
import com.example.phonebookmanager.controller.dto.CustomPageDto;
import com.example.phonebookmanager.domain.BridgeUser;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = {SwaggerConfig.USER_TAG})
public interface UserApi {

    @Operation(summary = "Creates contact.")
    @ApiResponse(responseCode = "201", description = "Successfully created contact")
    @PostMapping("/contact")
    ResponseEntity<Void> createContact(@Validated(ContactDto.OnCreate.class) @RequestBody ContactDto dto,
                                       @AuthenticationPrincipal BridgeUser bridgeUser);

    @Operation(summary = "Updates contact info.")
    @PutMapping("/contact/{id}")
    ResponseEntity<ContactDto> updateContact(@Validated(ContactDto.OnUpdate.class) @RequestBody ContactDto dto, @PathVariable Long id,
                                             @AuthenticationPrincipal BridgeUser bridgeUser);

    @Operation(summary = "Retrieves all contacts for specific user.")
    @GetMapping("/contact")
    ResponseEntity<CustomPageDto<ContactDto>> getAllContacts(@AuthenticationPrincipal BridgeUser bridgeUser, Pageable pageable);

    @Operation(summary = "URI location of created contact for specific user.")
    @GetMapping("/contact/{id}")
    ResponseEntity<ContactDto> getContact(@AuthenticationPrincipal BridgeUser bridgeUser, @PathVariable Long id);

    @Operation(summary = "Search user contacts by first name, last name or phone number.")
    @GetMapping("/contact/search")
    ResponseEntity<CustomPageDto<ContactDto>> searchContacts(@AuthenticationPrincipal BridgeUser bridgeUser,
                                                             @RequestParam(name = "param", required = false) String param, Pageable pageable);

    @Operation(summary = "Deletes user contact.")
    @DeleteMapping("/contact/{id}")
    ResponseEntity<Void> deleteContact(@AuthenticationPrincipal BridgeUser bridgeUser, @PathVariable Long id);
}
