package com.example.phonebookmanager.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRequestDto {

    @NotBlank(message = "Must not be blank", groups = OnCreate.class)
    @Email(message = "Email not valid", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @NotBlank(message = "Must not be blank", groups = OnCreate.class)
    @Pattern(regexp = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            message = "Password not valid", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Pattern(regexp = "ADMIN|USER", message = "Role not valid", groups = OnCreate.class)
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public interface OnCreate {
    }

    public interface OnUpdate {
    }
}
