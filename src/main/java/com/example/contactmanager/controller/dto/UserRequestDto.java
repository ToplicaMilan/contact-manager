package com.example.contactmanager.controller.dto;

import com.example.contactmanager.domain.entity.RoleType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRequestDto {

    @NotBlank(message = "Must not be blank", groups = OnCreate.class)
    @Email(message = "Not valid")
    private String email;

    @NotBlank(message = "Must not be blank", groups = OnCreate.class)
    @Pattern(regexp = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Not valid password pattern", groups = OnCreate.class)
    private String password;

    @NotBlank(message = "Must not be blank")
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

    public interface OnCreate {
    }
}
