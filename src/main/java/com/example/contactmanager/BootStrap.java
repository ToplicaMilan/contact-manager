package com.example.contactmanager;

import com.example.contactmanager.domain.entity.RoleType;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

//@Component
public class BootStrap {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void test() throws Exception {

        User user1 = new User();
        user1.setEmail("milan2");
        user1.setPassword(passwordEncoder.encode("1234"));
        user1.setRole(RoleType.ADMIN);
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("milan2@");
        user2.setPassword(passwordEncoder.encode("1234"));
        user2.setRole(RoleType.ADMIN);
        userRepository.save(user2);

        User user3 = new User();
        user3.setEmail("milan3");
        user3.setPassword(passwordEncoder.encode("Cnszdg91"));
        user3.setRole(RoleType.ADMIN);
        userRepository.save(user3);

        User user4 = new User();
        user4.setEmail("milan2.wittg2@gmail.com");
        user4.setPassword(passwordEncoder.encode("Cnszdg91"));
        user4.setRole(RoleType.ADMIN);
        userRepository.save(user4);


        System.out.println("Hello is anyone there");
    }
}
