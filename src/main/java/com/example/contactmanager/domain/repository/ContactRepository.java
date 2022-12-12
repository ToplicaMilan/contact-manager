package com.example.contactmanager.domain.repository;

import com.example.contactmanager.domain.entity.Contact;
import com.example.contactmanager.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    void deleteContactById(Long id);

    Page<Contact> findAllByUser(User user, Pageable pageable);

//    List<Contact> findAllByUserId(Long id);
}
