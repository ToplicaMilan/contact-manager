package com.example.contactmanager.service;

import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.controller.dto.UserCreationDto;
import com.example.contactmanager.controller.mapper.UserMapper;
import com.example.contactmanager.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRepoUserDetailService userDetails;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserRepoUserDetailService userDetails) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userDetails = userDetails;
    }

//    public UserDetails createUser(UserCreation dto) {
//        User user = userRepository.save(userMapper.userCreationDtoToEntity(dto));
//        user.setRole(RoleType.USER);
//        return userRepoUserDetailService.loadUserByUsername(user.getEmail());
//    }

    public UserCreationDto createUser(UserCreationDto dto) {
        User user = userRepository.save(userMapper.dtoToEntity(dto));
        return dto;
    }

    public Optional<User> getUser(String email) {
        var user = userRepository.findByEmail(email);
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
