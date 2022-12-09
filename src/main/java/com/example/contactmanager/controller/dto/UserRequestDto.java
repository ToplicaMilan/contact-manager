package com.example.contactmanager.controller.dto;

import com.example.contactmanager.domain.entity.RoleType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserRequestDto {

    //group pored message
    @NotEmpty(message = "Must not be empty")
    @Email(message = "Not valid")
    private String email;

    @NotEmpty
    @Pattern(regexp = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Not valid password pattern")
    private String password;

    @NotEmpty(message = "Must not be empty")
    private RoleType role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
