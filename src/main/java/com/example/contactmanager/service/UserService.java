package com.example.contactmanager.service;

import com.example.contactmanager.domain.entity.RoleType;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.dto.UserCreation;
import com.example.contactmanager.mapper.UserMapper;
import com.example.contactmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRepoUserDetailService userRepoUserDetailService;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserRepoUserDetailService userRepoUserDetailService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRepoUserDetailService = userRepoUserDetailService;
    }

    public UserDetails createUser(UserCreation dto) {
        User user = userRepository.save(userMapper.userCreationDtoToEntity(dto));
        user.setRole(RoleType.ADMIN);
        return userRepoUserDetailService.loadUserByUsername(user.getEmail());
    }

    public UserDetails getUser(String email) {
        return userRepoUserDetailService.loadUserByUsername(email);
    }
}
