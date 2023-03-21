package com.example.phonebookmanager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String mailUserName;
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendCreateConfirmation(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailUserName);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Registration Confirmation");
        simpleMailMessage.setText("Successfully registered");
        javaMailSender.send(simpleMailMessage);
    }

    public void sendUpdateConfirmation(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailUserName);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Registration Confirmation");
        simpleMailMessage.setText("Successfully updated");
        javaMailSender.send(simpleMailMessage);
    }
}
