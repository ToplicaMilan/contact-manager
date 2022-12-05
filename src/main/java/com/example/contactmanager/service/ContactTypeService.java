package com.example.contactmanager.service;

import com.example.contactmanager.domain.entity.ContactType;
import com.example.contactmanager.controller.dto.ContactTypeDto;
import com.example.contactmanager.controller.mapper.ContactTypeMapper;
import com.example.contactmanager.domain.repository.ContactTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactTypeService {

    private ContactTypeRepository ContactTypeRepository;
    private ContactTypeMapper contactTypeMapper;

    public ContactTypeService(ContactTypeRepository types, ContactTypeMapper typeMapper) {
        this.ContactTypeRepository = types;
        this.contactTypeMapper = typeMapper;
    }

    public void createContactType(ContactTypeDto dto) {
//        if (ContactTypeRepository.existsByTypeIgnoreCase(dto.getType())) {
//            throw new InvalidTypeException(InvalidTypeException.class.descriptorString());
//        }
        ContactType type = contactTypeMapper.dtoToEntity(dto);
        ContactTypeRepository.save(type);
    }
}
