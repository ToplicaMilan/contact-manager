package com.example.contactmanager.controller.mapper;

import com.example.contactmanager.controller.dto.ContactDto;
import com.example.contactmanager.controller.dto.CustomPageDto;
import com.example.contactmanager.domain.entity.Contact;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.service.ContactService;
import com.example.contactmanager.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class ContactMapper {

    private final ContactService contactService;

    public ContactMapper(ContactService contactService) {
        this.contactService = contactService;
    }

    public Contact mapToEntity(ContactDto dto) {
        Contact contact = new Contact();
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setAddress(dto.getAddress());
        contact.setPhoneNumber(dto.getPhoneNumber());
        return contact;
    }

    public ContactDto mapToDto(Contact contact) {
        ContactDto dto = new ContactDto();
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setAddress(contact.getAddress());
        dto.setPhoneNumber(contact.getPhoneNumber());
        dto.setType(contact.getContactType().getType());
        return dto;
    }

    public Contact updateContact(Contact contact, ContactDto dto) {

        if (nonNull(dto.getFirstName()) && !dto.getFirstName().isEmpty()) {
            contact.setFirstName(dto.getFirstName());
        }
        if (!dto.getLastName().isEmpty()) {
            contact.setLastName(dto.getLastName());
        }
        if (!dto.getAddress().isEmpty()) {
            contact.setAddress(dto.getAddress());
        }
        if (!dto.getPhoneNumber().isEmpty()) {
            contact.setPhoneNumber(dto.getPhoneNumber());
        }
        return contact;
    }

    public CustomPageDto mapToPageDto(User user, Pageable pageable) {
        var contacts = contactService.getAllUserContacts(user, pageable).map(this::mapToDto);
        return new CustomPageDto<>(contacts.getContent(), pageable.getPageNumber(), pageable.getPageSize(), contacts.getTotalElements());
    }
}
