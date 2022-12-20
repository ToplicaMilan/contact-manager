package com.example.contactmanager.service;

import com.example.contactmanager.domain.entity.ContactType;
import com.example.contactmanager.domain.repository.ContactTypeRepository;
import com.example.contactmanager.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;

    public ContactTypeService(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    public void saveContactType(ContactType contactType) {
        contactTypeRepository.save(contactType);
    }

    public ContactType findById(Long id) {
        return contactTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact Type does not exists"));
    }

    public ContactType findByType(String type) {
        return contactTypeRepository.findByTypeIgnoreCase(type)
                .orElseThrow(() -> new NotFoundException("Contact Type does not exists"));
    }

    public boolean existsByType(String type) {
        return contactTypeRepository.existsByTypeIgnoreCase(type);
    }

    public void deleteContactType(ContactType contactType) {
        contactTypeRepository.delete(contactType);
    }
}
