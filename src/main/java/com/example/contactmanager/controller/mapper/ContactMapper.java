package com.example.contactmanager.controller.mapper;

import com.example.contactmanager.controller.dto.ContactDto;
import com.example.contactmanager.domain.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

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
        return dto;
    }
}
