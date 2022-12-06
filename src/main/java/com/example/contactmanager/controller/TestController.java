package com.example.contactmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test/user")
    public String user() {
        return "Hey User Buddy";
    }

    @GetMapping("/test/admin")
    public String admin() {
        return "Hey Admin Bro";
    }
}
