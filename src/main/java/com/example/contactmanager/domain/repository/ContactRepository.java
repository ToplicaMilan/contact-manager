package com.example.contactmanager.domain.repository;

import com.example.contactmanager.domain.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsByPhoneNumber(String phoneNumber);
}
