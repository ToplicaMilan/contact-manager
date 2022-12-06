package com.example.contactmanager.service;

import com.example.contactmanager.domain.entity.ContactType;
import com.example.contactmanager.controller.dto.ContactTypeDto;
import com.example.contactmanager.controller.mapper.ContactTypeMapper;
import com.example.contactmanager.domain.repository.ContactTypeRepository;
import com.example.contactmanager.exception.ContactTypeException;
import org.springframework.stereotype.Service;

@Service
public class ContactTypeService {

    private ContactTypeRepository ContactTypeRepository;
    private ContactTypeMapper contactTypeMapper;

    public ContactTypeService(ContactTypeRepository contactTypeRepository, ContactTypeMapper typeMapper) {
        this.ContactTypeRepository = contactTypeRepository;
        this.contactTypeMapper = typeMapper;
    }

    public void createContactType(ContactTypeDto dto) {
        if (ContactTypeRepository.existsByTypeIgnoreCase(dto.getType())) {
            throw new ContactTypeException("Contact Type already exists");
        }
        ContactType type = contactTypeMapper.dtoToEntity(dto);
        ContactTypeRepository.save(type);
    }
}
