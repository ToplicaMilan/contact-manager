package com.example.phonebookmanager.domain.repository;

import com.example.phonebookmanager.domain.entity.Contact;
import com.example.phonebookmanager.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsById(Long id);

    Optional<Contact> findById(Long id);

    Page<Contact> findAllByUser(User user, Pageable pageable);

    Page<Contact> findAllByUserAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneNumberContaining(User user, String firstName, String lastName, String number, Pageable pageable);
}
