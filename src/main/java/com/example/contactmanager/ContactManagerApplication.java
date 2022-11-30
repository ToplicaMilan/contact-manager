package com.example.contactmanager;

import com.example.contactmanager.domain.entity.RoleType;
import com.example.contactmanager.domain.entity.User;
import com.example.contactmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactManagerApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository userRepository) {
//        return args -> {
//            userRepository.save(new User(1L, "milan@gmail", "Cnszdg91", RoleType.USER));
//        };
//    }
}
