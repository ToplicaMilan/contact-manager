package com.example.contactmanager.service;

import com.example.contactmanager.controller.mapper.ContactTypeMapper;
import com.example.contactmanager.domain.entity.ContactType;
import com.example.contactmanager.domain.repository.ContactTypeRepository;
import com.example.contactmanager.exception.ContactTypeException;
import org.springframework.stereotype.Service;

@Service
public class ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;

    public ContactTypeService(ContactTypeRepository contactTypeRepository, ContactTypeMapper typeMapper) {
        this.contactTypeRepository = contactTypeRepository;
        this.contactTypeMapper = typeMapper;
    }

    public void saveContactType(ContactType contactType) {
        contactTypeRepository.save(contactType);
    }

    public ContactType findById(Long id) {
        return contactTypeRepository.findById(id)
                .orElseThrow(() -> new ContactTypeException("Contact Type does not exists"));
    }

    public ContactType findByType(String type) {
        return contactTypeRepository.findByTypeIgnoreCase(type)
                .orElseThrow(() -> new ContactTypeException("Contact Type does not exists"));
    }

    public void deleteContactType(ContactType contactType) {
        contactTypeRepository.delete(contactType);
    }

}
