package com.example.phonebookmanager.service;

import com.example.phonebookmanager.domain.entity.Contact;
import com.example.phonebookmanager.domain.entity.User;
import com.example.phonebookmanager.domain.repository.ContactRepository;
import com.example.phonebookmanager.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact findById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact does not exists"));
    }

    public boolean existsById(Long id) {
        return contactRepository.existsById(id);
    }

    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }

    public Page<Contact> getAllUserContacts(User user, Pageable pageable) {
        return contactRepository.findAllByUser(user, pageable);
    }

    public Contact getUserContacts(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact not found"));
    }

    public Page<Contact> searchUserContacts(User user, String param, Pageable pageable) {
        return contactRepository
                .findAllByUserAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneNumberContaining
                        (user, param, param, param, pageable);
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }
}
