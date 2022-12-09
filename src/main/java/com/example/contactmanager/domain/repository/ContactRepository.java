package com.example.contactmanager.domain.repository;

import com.example.contactmanager.domain.entity.Contact;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    void deleteContactById(Long id);

    List<Contact> findAllByUserId(Long id, PageRequest pageRequest);

//    List<Contact> findAllByUserId(Long id);
}
