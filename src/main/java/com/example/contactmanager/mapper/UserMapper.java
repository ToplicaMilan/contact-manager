package com.example.contactmanager.mapper;

import com.example.contactmanager.domain.entity.RoleType;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.dto.UserCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public User userCreationDtoToEntity(UserCreation dto){
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(RoleType.ADMIN);
        return user;
    }
}
