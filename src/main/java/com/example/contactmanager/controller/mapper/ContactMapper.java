package com.example.contactmanager.controller.mapper;

import com.example.contactmanager.controller.dto.ContactDto;
import com.example.contactmanager.controller.dto.CustomPageDto;
import com.example.contactmanager.domain.entity.Contact;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.service.ContactService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    private final ContactService contactService;

    public ContactMapper(ContactService contactService) {
        this.contactService = contactService;
    }

    public Contact mapToEntity(ContactDto dto) {
        var contact = new Contact();
        contact.setFirstName(dto.getFirstName());
        if (!dto.getLastName().isBlank()) {
            contact.setLastName(dto.getLastName());
        }
        if (!dto.getAddress().isBlank()) {
            contact.setAddress(dto.getAddress());
        }
        contact.setPhoneNumber(dto.getPhoneNumber());
        return contact;
    }

    public ContactDto mapToDto(Contact contact) {
        var dto = new ContactDto();
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setAddress(contact.getAddress());
        dto.setPhoneNumber(contact.getPhoneNumber());
        dto.setType(contact.getContactType().getType());
        return dto;
    }

    public Contact updateContact(Contact contact, ContactDto dto) {
        if (!dto.getFirstName().isBlank()) {
            contact.setFirstName(dto.getFirstName());
        }
        if (!dto.getLastName().isBlank()) {
            contact.setLastName(dto.getLastName());
        }
        if (!dto.getAddress().isBlank()) {
            contact.setAddress(dto.getAddress());
        }
        if (!dto.getPhoneNumber().isBlank()) {
            contact.setPhoneNumber(dto.getPhoneNumber());
        }
        return contact;
    }

    public CustomPageDto mapToPageDto(User user, Pageable pageable) {

        var contacts = contactService.getAllUserContacts(user, pageable).map(this::mapToDto);
        return new CustomPageDto<>(contacts.getContent(), pageable.getPageNumber(), pageable.getPageSize(), contacts.getTotalElements());
    }
}
