package com.example.contactmanager.controller.mapper;

import com.example.contactmanager.controller.dto.UserCreationDto;
import com.example.contactmanager.domain.entity.RoleType;
import com.example.contactmanager.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
// moze i kao static field
    @Autowired
    protected PasswordEncoder passwordEncoder;
//map
    public User mapToEntity(UserCreationDto dto){
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return user;
    }
}
