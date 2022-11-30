package com.example.contactmanager.service;

import com.example.contactmanager.domain.BridgeUser;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepoUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserRepoUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        BridgeUser bridgeUser = null;
        if (optionalUser.isPresent())
            bridgeUser = new BridgeUser(optionalUser.get());
        return bridgeUser;
//        return userRepository.findByEmail(email)
//                .map(BridgeUser::new)
//                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
