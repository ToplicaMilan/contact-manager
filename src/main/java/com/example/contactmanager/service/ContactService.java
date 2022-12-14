package com.example.contactmanager.service;

import com.example.contactmanager.domain.entity.Contact;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.domain.repository.ContactRepository;
import com.example.contactmanager.service.exception.NotFoundException;
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

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }
}
