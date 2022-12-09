package com.example.contactmanager.service;

import com.example.contactmanager.domain.entity.Contact;
import com.example.contactmanager.domain.entity.ContactType;
import com.example.contactmanager.domain.repository.ContactRepository;
import com.example.contactmanager.exception.ContactException;
import com.example.contactmanager.exception.ContactTypeException;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact findById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactException("Contact does not exists"));
    }

    public void saveContact(Contact contact) {
//        if (contactRepository.existsByPhoneNumber(contact.getPhoneNumber())) {
//            throw new ContactException("Phone number already exists");
//        }
        contactRepository.save(contact);
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }
}
