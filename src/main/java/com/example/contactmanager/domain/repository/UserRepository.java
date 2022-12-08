package com.example.contactmanager.domain.repository;

import com.example.contactmanager.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAll();

    boolean existsByEmail(String email);

    @Override
    <S extends User> S save(S entity);

    @Override
    Optional<User> findById(Long id);
}
