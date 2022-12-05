package com.example.contactmanager.service;

import com.example.contactmanager.domain.BridgeUser;
import com.example.contactmanager.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserRepoUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserRepoUserDetailService(UserRepository users) {
        this.userRepository = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(BridgeUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
