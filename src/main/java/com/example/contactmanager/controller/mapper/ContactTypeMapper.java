package com.example.contactmanager.controller.mapper;

import com.example.contactmanager.domain.entity.ContactType;
import com.example.contactmanager.controller.dto.ContactTypeDto;
import org.springframework.stereotype.Component;

@Component
public class ContactTypeMapper {

    public ContactType dtoToEntity(ContactTypeDto dto) {

        ContactType type = new ContactType();
        type.setDescription(dto.getDescription());
        type.setType(dto.getType());
        return type;
    }
}
