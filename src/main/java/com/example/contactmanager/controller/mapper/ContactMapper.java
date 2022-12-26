package com.example.contactmanager.controller.mapper;

import com.example.contactmanager.controller.dto.ContactDto;
import com.example.contactmanager.controller.dto.CustomPageDto;
import com.example.contactmanager.domain.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class ContactMapper {

    public Contact mapToEntity(ContactDto dto) {
        var contact = new Contact();
        contact.setFirstName(dto.getFirstName());
        if (nonNull(dto.getLastName()) && !dto.getLastName().isBlank()) {
            contact.setLastName(dto.getLastName());
        }
        if (nonNull(dto.getAddress()) && !dto.getAddress().isBlank()) {
            contact.setAddress(dto.getAddress());
        }
        if (nonNull(dto.getCountry()) && !dto.getCountry().isBlank()) {
            contact.setCountry(dto.getCountry());
        }
        contact.setPhoneNumber(dto.getPhoneNumber());
        return contact;
    }

    public ContactDto mapToDto(Contact contact) {
        var dto = new ContactDto();
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setAddress(contact.getAddress());
        dto.setCountry(contact.getCountry());
        dto.setPhoneNumber(contact.getPhoneNumber());
        dto.setType(contact.getContactType().getType());
        return dto;
    }

    public Contact updateContact(Contact contact, ContactDto dto) {
        if (nonNull(dto.getFirstName()) && !dto.getFirstName().isBlank()) {
            contact.setFirstName(dto.getFirstName());
        }
        if (nonNull(dto.getLastName()) && !dto.getLastName().isBlank()) {
            contact.setLastName(dto.getLastName());
        }
        if (nonNull(dto.getAddress()) && !dto.getAddress().isBlank()) {
            contact.setAddress(dto.getAddress());
        }
        if (nonNull(dto.getCountry()) && !dto.getCountry().isBlank()) {
            contact.setCountry(dto.getCountry());
        }
        contact.setPhoneNumber(dto.getPhoneNumber());
        return contact;
    }

    public CustomPageDto<ContactDto> mapToPageDto(Page<Contact> contacts) {
        var dtos = contacts.map(this::mapToDto);
        return new CustomPageDto<>(dtos.getContent(),
                dtos.getPageable().getPageNumber(),
                dtos.getPageable().getPageSize(),
                dtos.getTotalElements());
    }
}
