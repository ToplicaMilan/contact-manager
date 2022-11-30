package com.example.contactmanager;

import com.example.contactmanager.domain.entity.RoleType;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class BootStrap implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User user = new User("milan@gmail", "Cnszdg91");
        userRepository.save(user);

        System.out.println("Hello is anyone there");
    }
}
